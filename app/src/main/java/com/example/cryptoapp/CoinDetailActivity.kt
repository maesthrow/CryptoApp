package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }

    private lateinit var binding: ActivityCoinDetailBinding

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, Observer { coinPriceInfo ->
                with(binding) {
                    with(coinPriceInfo) {
                        tvFromSymbol.text = fromSymbol.toString()
                        tvToSymbol.text = toSymbol.toString()
                        tvPrice.text = price.toString()
                        tvMinPrice.text = lowDay.toString()
                        tvMaxPrice.text = highDay.toString()
                        tvLastMarket.text = lastMarket.toString()
                        tvLastUpdate.text = getFormattedTime()
                        Picasso.get().load(getFullImageUrl()).into(ivLogoCoin)
                    }
                }
            })
        }
    }
}