package com.example.derich.bizwiz.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.derich.bizwiz.R;

import java.util.List;

public class ProductAdapter  extends ArrayAdapter<Products> {

    //storing all the names in the list
    private List<Products> products;

    //context object
    private Context context;

    //constructor
    public ProductAdapter(Context context, int resource, List<Products> products) {
        super(context, resource, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview items
        View listViewItem = inflater.inflate(R.layout.display_products, null, false);
        TextView textViewName = listViewItem.findViewById(R.id.textViewProductName);
        TextView textViewQuantity = listViewItem.findViewById(R.id.textViewQuantity);
        TextView textViewBuyingPrice = listViewItem.findViewById(R.id.textViewProductBuyingPrice);
        TextView textViewRetailPrice = listViewItem.findViewById(R.id.textViewProductRetailPrice);
        TextView textViewWholesalePrice = listViewItem.findViewById(R.id.textViewProductWholesalePrice);
        ImageView imageViewStatus = listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        Products product = products.get(position);

        //setting the name to textview
        textViewName.setText("Name: " + product.getProduct_name());
        textViewQuantity.setText("Quantity: " + product.getQuantity());
        textViewBuyingPrice.setText("Buying Price: " + product.getProduct_buying_price());
        textViewRetailPrice.setText("Retail Price: " + product.getProduct_retail_price());
        textViewWholesalePrice.setText("Wholesale Price: " + product.getProduct_wholesale_price());
        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon
        if (product.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);

        return listViewItem;
    }
}