package com.example.kotlin_practice

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Row(
    val ADRES: String,
    val CODE_VALUE: String,
    val FDRM_CLOSE_DATE: String,
    val GU_CODE: String,
    val HMPG_URL: String,
    val LBRRY_NAME: String,
    val LBRRY_SEQ_NO: String,
    val LBRRY_SE_NAME: String,
    val OP_TIME: String,
    val TEL_NO: String,
    val XCNTS: String,
    val YDNTS: String
):ClusterItem{
    override fun hashCode(): Int = LBRRY_SEQ_NO.toInt()

    override fun getPosition(): LatLng = LatLng(XCNTS.toDouble(), YDNTS.toDouble())

    override fun getTitle(): String? = LBRRY_NAME

    override fun getSnippet(): String? = ADRES
}