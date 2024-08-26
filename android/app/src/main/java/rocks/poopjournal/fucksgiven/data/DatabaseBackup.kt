package rocks.poopjournal.fucksgiven.data

import android.content.Context
import android.content.Intent
import android.widget.Toast
import rocks.poopjournal.fucksgiven.presentation.ui.utils.SQLITE_SHMFILE_SUFFIX
import rocks.poopjournal.fucksgiven.presentation.ui.utils.SQLITE_WALFILE_SUFFIX
import rocks.poopjournal.fucksgiven.presentation.ui.utils.THEDATABASE_DATABASE_BACKUP_SUFFIX
import rocks.poopjournal.fucksgiven.presentation.ui.utils.THEDATABASE_DATABASE_NAME
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseBackupManager @Inject constructor(
    private val context: Context,
    private val fuckDatabase: FuckDatabase
) {
    fun backupDatabase(message : String): Int {
        var result = -99
        val dbFile = context.getDatabasePath(THEDATABASE_DATABASE_NAME)
        val dbWalFile = File(dbFile.path + SQLITE_WALFILE_SUFFIX)
        val dbShmFile = File(dbFile.path + SQLITE_SHMFILE_SUFFIX)
        val bkpFile = File(dbFile.path + THEDATABASE_DATABASE_BACKUP_SUFFIX)
        val bkpWalFile = File(bkpFile.path + SQLITE_WALFILE_SUFFIX)
        val bkpShmFile = File(bkpFile.path + SQLITE_SHMFILE_SUFFIX)
        if (bkpFile.exists()) bkpFile.delete()
        if (bkpWalFile.exists()) bkpWalFile.delete()
        if (bkpShmFile.exists()) bkpShmFile.delete()
        checkpoint()
        try {
            dbFile.copyTo(bkpFile, true)
            if (dbWalFile.exists()) dbWalFile.copyTo(bkpWalFile, true)
            if (dbShmFile.exists()) dbShmFile.copyTo(bkpShmFile, true)
            result = 0
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    fun restoreDatabase(restart: Boolean = true) {
        val backupFilePath =
            context.getDatabasePath(THEDATABASE_DATABASE_NAME).path + THEDATABASE_DATABASE_BACKUP_SUFFIX
        if (!File(backupFilePath).exists()) {
            return
        }
        if (fuckDatabase == null) return
        val dbPath = fuckDatabase.openHelper.readableDatabase.path
        val dbFile = File(dbPath)
        val dbWalFile = File(dbFile.path + SQLITE_WALFILE_SUFFIX)
        val dbShmFile = File(dbFile.path + SQLITE_SHMFILE_SUFFIX)
        val bkpFile = File(dbFile.path + THEDATABASE_DATABASE_BACKUP_SUFFIX)
        val bkpWalFile = File(bkpFile.path + SQLITE_WALFILE_SUFFIX)
        val bkpShmFile = File(bkpFile.path + SQLITE_SHMFILE_SUFFIX)
        try {
            bkpFile.copyTo(dbFile, true)
            if (bkpWalFile.exists()) bkpWalFile.copyTo(dbWalFile, true)
            if (bkpShmFile.exists()) bkpShmFile.copyTo(dbShmFile, true)
            checkpoint()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (restart) {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
            System.exit(0)
        }
    }

    private fun checkpoint() {
        val db = fuckDatabase.openHelper.writableDatabase
        db.query("PRAGMA wal_checkpoint(FULL);", emptyArray())
        db.query("PRAGMA wal_checkpoint(TRUNCATE);", emptyArray())
    }
}