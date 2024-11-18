package com.example.lab1

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.IOException

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val externalFileName = "15.txt"
    private val internalFileName = "backup_15.txt"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreManager = DataStoreManager(requireContext())
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Обновляем состояние переключателей
        runBlocking {
            val isDarkTheme = dataStoreManager.isDarkThemeFlow.first()
            binding.switchDarkTheme.isChecked = isDarkTheme
        }
        binding.switchNotifications.isChecked = sharedPreferencesManager.areNotificationsEnabled()
        updateFileStatus()//надписи о копии

        // Обрабатываем переключение темы и уведомлений
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            runBlocking {
                dataStoreManager.saveTheme(isChecked)
            }
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferencesManager.setNotificationsEnabled(isChecked)
        }

        binding.deleteFileButton.setOnClickListener { // Удаление файла
            val file = getExternalFile()
            if (file.exists()) {
                createBackup(file)
                file.delete()
                Toast.makeText(requireContext(), "File deleted successfully", Toast.LENGTH_SHORT).show()
                updateFileStatus()
            }
        }

        binding.restoreFileButton.setOnClickListener { // Восстановление файла
            val backupFile = getInternalFile()
            if (backupFile.exists()) {
                restoreFile(backupFile)
                updateFileStatus()
            } else {
                Toast.makeText(requireContext(), "No backup found to restore", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFileStatus() {
        val externalFile = getExternalFile()
        val internalFile = getInternalFile()
        if (externalFile.exists()) {
            binding.fileStatusText.text = "File exists: ${externalFile.absolutePath}"
            binding.deleteFileButton.visibility = View.VISIBLE
            binding.restoreFileButton.visibility = View.GONE
        } else if (internalFile.exists()) {
            binding.fileStatusText.text = "Backup available: Ready to restore"
            binding.deleteFileButton.visibility = View.GONE
            binding.restoreFileButton.visibility = View.VISIBLE
        } else {
            binding.fileStatusText.text = "File does not exist"
            binding.deleteFileButton.visibility = View.GONE
            binding.restoreFileButton.visibility = View.GONE
        }
    }

    private fun getExternalFile(): File {
        val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) //внутренее хранилище
        return File(documentsDir, externalFileName)
    }


    private fun getInternalFile(): File {
        return File(requireContext().filesDir, internalFileName) //внешнее
    }

    private fun createBackup(file: File) {
        try {
            val backupFile = getInternalFile()
            file.copyTo(backupFile, overwrite = true)
            Toast.makeText(requireContext(), "Backup created successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Failed to create backup: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun restoreFile(backupFile: File) {
        try {
            val restoredFile = getExternalFile()
            backupFile.copyTo(restoredFile, overwrite = true)
            Toast.makeText(requireContext(), "File restored successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(requireContext(), "Failed to restore file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
