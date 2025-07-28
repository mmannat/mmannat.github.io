package edu.csueb.android.mylocationsapp

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher

class LocationsContentProvider : ContentProvider() {
    private lateinit var dbHelper: LocationsDB

    companion object {
        const val AUTHORITY = "edu.csueb.android.mylocationsapp.locations"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/locations")
        private const val LOCATIONS = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "locations", LOCATIONS)
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = LocationsDB(context!!)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            LOCATIONS -> {
                val db = dbHelper.writableDatabase
                val id = db.insert(LocationsDB.TABLE_NAME, null, values)
                Uri.withAppendedPath(CONTENT_URI, id.toString())
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (uriMatcher.match(uri)) {
            LOCATIONS -> {
                val db = dbHelper.writableDatabase
                db.delete(LocationsDB.TABLE_NAME, selection, selectionArgs)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            LOCATIONS -> {
                val db = dbHelper.readableDatabase
                db.query(
                    LocationsDB.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?) = 0

    override fun getType(uri: Uri): String? = null
}
