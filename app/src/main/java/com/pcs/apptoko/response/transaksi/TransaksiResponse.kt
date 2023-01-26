package com.pcs.apptoko.response.transaksi

data class TransaksiResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)