package com.heroku.myapp.randomcolour.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.heroku.myapp.randomcolour.R
import com.heroku.myapp.randomcolour.presentation.common.LiveDataEventObserver
import com.heroku.myapp.randomcolour.presentation.common.error.ErrorViewType
import com.heroku.myapp.randomcolour.presentation.common.error.UiError
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLoadingObservable()
            .observe(viewLifecycleOwner, Observer { loading(it) })
        viewModel.getContentObservable()
            .observe(viewLifecycleOwner, LiveDataEventObserver { content(it) })
        viewModel.getErrorObservable()
            .observe(viewLifecycleOwner, LiveDataEventObserver { error(it) })

        bt_main.setOnClickListener {
            viewModel.getManualRefreshColours()
        }
        viewModel.getInitialColours()
    }

    private fun loading(visible: Boolean) {
        pb_main.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun content(state: MainViewModel.State) {
        when (state) {
            is MainViewModel.State.Content -> {
                tv_main_word.text = state.word
                context?.let {
                    iv_main.setBackgroundColor(state.colour)
                }
            }
        }
    }

    private fun error(error: UiError) {
        when {
            ::alertDialog.isInitialized && alertDialog.isShowing -> {
                // noop
            }
            else -> {
                activity?.let {
                    when (error.errorViewType) {
                        ErrorViewType.DIALOG -> {
                            alertDialog = AlertDialog.Builder(it)
                                .setTitle(error.title)
                                .setMessage(error.message)
                                .setPositiveButton(error.positiveAction) { dialog, _ ->
                                    dialog.dismiss()
                                    viewModel.getManualRefreshColours()
                                }
                                .setCancelable(error.cancelable)
                                .show()
                        }
                        else -> {
                        } // noop
                    }
                }
            }
        }
    }
}
