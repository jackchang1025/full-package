package com.storm.safe.rock.service.account

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Stub ContentProvider for the account sync framework.
 *
 * JADX reference: service/account/ptbsfbak.java (46 LOC)
 * A no-op ContentProvider required by Android's sync adapter framework.
 * All CRUD methods return null/0 — the provider exists solely to satisfy
 * the sync framework's requirement for a content authority.
 */
class StubContentProvider : ContentProvider() {

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}
