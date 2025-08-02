package br.com.flemis.bookishadventure.utils

interface PermissionListener {
    fun requestPermission(permission: String)
    fun requestPermissions(permissions: List<String>)
    fun getPermissionStatus(permission: String): PermissionStatus
    fun openAppSetings()
}