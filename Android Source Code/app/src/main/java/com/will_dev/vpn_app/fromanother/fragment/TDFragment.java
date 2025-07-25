package com.will_dev.vpn_app.fromanother.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.will_dev.vpn_app.R;
import com.will_dev.vpn_app.fromanother.activity.TDView;
import com.will_dev.vpn_app.fromanother.util.util.API;
import com.will_dev.vpn_app.fromanother.util.util.Constant;
import com.will_dev.vpn_app.fromanother.util.util.Method;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TDFragment extends Fragment {

    private Method method;
    private ImageView imageView;
    private ProgressBar progressBar;
    private RelativeLayout relMain;
    private MaterialTextView textView_noData, textView_msg, textView_payment, textView_date, textView_requestDate,
            textView_responseDate, textView_point, textView_payment_mode, textView_bankDetail;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.td_fragment, container, false);


        method = new Method(getActivity());


        ImageView goBack = view.findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        assert getArguments() != null;
        String redeemId = getArguments().getString("redeem_id");

        relMain = view.findViewById(R.id.rel_main_td);
        progressBar = view.findViewById(R.id.progressbar_td);
        textView_noData = view.findViewById(R.id.textView_noData_td);
        imageView = view.findViewById(R.id.imageView_td);
        textView_msg = view.findViewById(R.id.textView_msg_td);
        textView_payment = view.findViewById(R.id.textView_payment_td);
        textView_date = view.findViewById(R.id.textView_date_td);
        textView_requestDate = view.findViewById(R.id.textView_requestDate_td);
        textView_responseDate = view.findViewById(R.id.textView_responseDate_td);
        textView_point = view.findViewById(R.id.textView_point_td);
        textView_payment_mode = view.findViewById(R.id.textView_payment_mode_td);
        textView_bankDetail = view.findViewById(R.id.textView_bankDetail_td);

        relMain.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        textView_noData.setVisibility(View.GONE);

        if (method.isNetworkAvailable()) {
            detail(redeemId);
        } else {
            method.alertBox(getResources().getString(R.string.internet_connection));
        }

        setHasOptionsMenu(false);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NotNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void detail(String redeem_id) {

        if (getActivity() != null) {

            progressBar.setVisibility(View.VISIBLE);

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(getActivity()));
            jsObj.addProperty("method_name", "get_transaction");
            jsObj.addProperty("redeem_id", redeem_id);
            params.put("data", API.toBase64(jsObj.toString()));
            client.post(Constant.url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    if (getActivity() != null) {

                        String res = new String(responseBody);

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            if (jsonObject.has(Constant.STATUS)) {

                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                if (status.equals("-2")) {
                                    method.suspend(message);
                                } else {
                                    method.alertBox(message);
                                }

                                textView_noData.setVisibility(View.VISIBLE);

                            } else {

                                JSONObject object = jsonObject.getJSONObject(Constant.tag);
                                String success = object.getString("success");
                                String redeem_id = object.getString("redeem_id");
                                String user_points = object.getString("user_points");
                                String redeem_price = object.getString("redeem_price");
                                String payment_mode = object.getString("payment_mode");
                                String bank_details = object.getString("bank_details");
                                String request_date = object.getString("request_date");
                                String custom_message = object.getString("cust_message");
                                String receipt_img = object.getString("receipt_img");
                                String response_date = object.getString("responce_date");
                                String status = object.getString("status");

                                if (success.equals("1")) {

                                    if (!receipt_img.equals("")) {
                                        Glide.with(getActivity().getApplicationContext()).load(receipt_img)
                                                .placeholder(R.drawable.logo)
                                                .into(imageView);

                                        imageView.setOnClickListener(view -> startActivity(new Intent(getActivity(), TDView.class)
                                                .putExtra("path", receipt_img)));

                                    }

                                    if (status.equals("1")) {
                                        textView_date.setTextColor(getResources().getColor(R.color.green));
                                        textView_date.setText(getResources().getString(R.string.approve_date));
                                    } else {
                                        textView_date.setTextColor(getResources().getColor(R.color.red));
                                        textView_date.setText(getResources().getString(R.string.reject_date));
                                    }

                                    textView_msg.setText(custom_message);
                                    textView_payment.setText(redeem_price);
                                    textView_requestDate.setText(request_date);
                                    textView_responseDate.setText(response_date);
                                    textView_point.setText(user_points);
                                    textView_payment_mode.setText(payment_mode);
                                    textView_bankDetail.setText(Html.fromHtml(bank_details));

                                    relMain.setVisibility(View.VISIBLE);

                                } else {
                                    textView_noData.setVisibility(View.VISIBLE);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            method.alertBox(getResources().getString(R.string.failed_try_again));
                        }

                    }

                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressBar.setVisibility(View.GONE);
                    textView_noData.setVisibility(View.VISIBLE);
                    method.alertBox(getResources().getString(R.string.failed_try_again));
                }
            });

        }

    }

}
