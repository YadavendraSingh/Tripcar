package tripcar.yadu.com.tripcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Collections;
import java.util.List;

import tripcar.yadu.com.tripcar.CarDetailActivity;
import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.models.Datum;

/**
 * Created by yadu on 13/2/16.
 */
public class FindCarAdapter extends RecyclerView.Adapter<FindCarAdapter.MyViewHolder> {

    private LayoutInflater inflater;

    List<Datum> data= Collections.emptyList();

    Context context;

    public FindCarAdapter(Context context, List<Datum> data){

        inflater = LayoutInflater.from(context);
        this.data=data;
        this.context = context;

        FacebookSdk.sdkInitialize(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.find_car_item,parent,false);

        MyViewHolder holder=new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Datum current=data.get(position);

        String to_from = current.getDepartureCity()+" "+context.getResources().getString(R.string.arrow)+" "+current.getArrivalCity();
        String name = current.getUser().getFirstName()+" "+current.getUser().getLastName();
        String make_n_model = current.getCar().getMake()+" "+current.getCar().getModel();
        String type_n_color = current.getCar().getCarType() + " " + current.getCar().getModel();
        String price = context.getResources().getString(R.string.Rs) + current.getPrice();

        holder.tv_time.setText(current.getDepartureTime());
        holder.tv_price.setText(price);
        holder.tv_from_to.setText(to_from);
        holder.tv_seats_available.setText(current.getSeats()+" seats available");
        holder.tvname.setText(name);
        holder.tv_make_n_model.setText(make_n_model);
        holder.tv_type_n_color.setText(type_n_color);

        holder.user_img.setProfileId(current.getUser().getFbId());

        holder.user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // FindCarActivity activity = (FindCarActivity) context;

                Intent in = new Intent(context, CarDetailActivity.class);

                context.startActivity(in);

                // activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });

        //holder.layout_cardetail

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_time, tv_price, tv_from_to, tv_seats_available, tvname, tv_type_n_color, tv_make_n_model;
        ProfilePictureView user_img;
        LinearLayout layout_cardetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_time=(TextView) itemView.findViewById(R.id.tv_time);
            tv_price=(TextView) itemView.findViewById(R.id.tv_price);
            tv_from_to=(TextView) itemView.findViewById(R.id.tv_from_to);
            tv_seats_available=(TextView) itemView.findViewById(R.id.tv_seats_available);
            tvname=(TextView) itemView.findViewById(R.id.tvname);
            tv_type_n_color=(TextView) itemView.findViewById(R.id.tv_type_n_color);
            tv_make_n_model=(TextView) itemView.findViewById(R.id.tv_make_n_model);

            user_img=(ProfilePictureView) itemView.findViewById(R.id.profile_pic);

            layout_cardetail = (LinearLayout) itemView.findViewById(R.id.layout_cardetail);
        }
    }
}
