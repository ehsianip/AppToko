package com.pcs.apptoko

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.TextWatcherAdapter
import com.pcs.apptoko.api.BaseRetrofit
import com.pcs.apptoko.response.cart.Cart
import com.pcs.apptoko.response.itemTransaksi.ItemTransaksiResponsePost
import com.pcs.apptoko.response.produk.ProdukResponsePost
import com.pcs.apptoko.response.transaksi.TransaksiResponsePost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class BayarFragment : Fragment() {

    private val api by lazy { BaseRetrofit().endPoint}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bayar, container, false)

        val total = arguments?.getString("TOTAL")
        val my_cart = arguments?.getParcelableArrayList<Cart>("MY_CART")

        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)

        val txtKembalian = view.findViewById<TextView>(R.id.txtKembalian)
        val txtTotalTransaksiBayar = view.findViewById<TextView>(R.id.txtTotalTransaksiBayar)
        txtTotalTransaksiBayar.setText(numberFormat.format(total.toString().toDouble()).toString())

        val txtPenerimaan = view.findViewById<EditText>(R.id.txtPenerimaan)
        txtPenerimaan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                if(txtPenerimaan.text.toString()!=" "){
                    val penerimaan:Int = txtPenerimaan.text.toString().toInt()
                    val kembalian = penerimaan - total.toString().toInt()

                    if(kembalian>0){
                        txtKembalian.setText(numberFormat.format(kembalian.toDouble()).toString())
                    }else{
                        txtKembalian.setText(numberFormat.format(0).toString())
                    }
                }
            }

        })

        val token = LoginActivity.sessionManager.getString("TOKEN")
        val adminId = LoginActivity.sessionManager.getString("ADMIN_ID")

        val btnSimpanBayar = view.findViewById<Button>(R.id.btnSimpanBayar)
        btnSimpanBayar.setOnClickListener {
            api.postTransaksi(token.toString(), adminId.toString().toInt(), total.toString().toInt()).enqueue(object : Callback<TransaksiResponsePost>{
                override fun onResponse(
                    call: Call<TransaksiResponsePost>,
                    response: Response<TransaksiResponsePost>,
                ) {
                    val id_transaksi = response.body()!!.data.transaksi.id
                    Log.e("id_transaksi", id_transaksi.toString())

                    for(item in my_cart!!){
                        api.postItemTransaksi(token.toString(),id_transaksi.toString().toInt(),item.id,item.qty,item.harga).enqueue(object : Callback<ItemTransaksiResponsePost>{
                            override fun onResponse(
                                call: Call<ItemTransaksiResponsePost>,
                                response: Response<ItemTransaksiResponsePost>,
                            ) {

                            }

                            override fun onFailure(
                                call: Call<ItemTransaksiResponsePost>,
                                t: Throwable,
                            ) {
                                Log.e("Error", t.toString())
                            }

                        })
                    }

                    Toast.makeText(view.context, "Transaksi di Simpan",Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.transaksiFragment)

                }

                override fun onFailure(call: Call<TransaksiResponsePost>, t: Throwable) {
                    Log.e("Error", t.toString())
                }

            })
        }

        return view
    }
}