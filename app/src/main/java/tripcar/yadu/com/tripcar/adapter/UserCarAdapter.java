package tripcar.yadu.com.tripcar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.gettersetter.UserCarGetterSetter;

/**
 * Created by yadu on 30/1/16.
 */
public class UserCarAdapter extends RecyclerView.Adapter<UserCarAdapter.MyViewHolder> {


    private LayoutInflater inflater;

    List<UserCarGetterSetter> data= Collections.emptyList();

    Context context;

    public UserCarAdapter(Context context, List<UserCarGetterSetter> data){

        inflater = LayoutInflater.from(context);
        this.data=data;
        this.context = context;

    }



    @Override
    public UserCarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.user_car_item,parent,false);

        MyViewHolder holder=new MyViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(UserCarAdapter.MyViewHolder holder, int position) {

        final UserCarGetterSetter current=data.get(position);

        holder.tv_brand_n_model.setText(current.getMake()+" "+current.getModel());
       //holder.tv_color.setText(current.getColourc());
        holder.tv_comfort.setText(current.getComfort());
        holder.tv_car_type.setText(current.getCar_type());
        holder.tv_seat.setText(current.getSeats());
        holder.tv_number.setText(current.getNumberp());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_brand_n_model,tv_color,tv_comfort,tv_seat,tv_number,tv_car_type;
        ImageView car_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_brand_n_model=(TextView) itemView.findViewById(R.id.tv_car_brand_n_model);
            tv_color=(TextView) itemView.findViewById(R.id.tvcolor);
            tv_comfort=(TextView) itemView.findViewById(R.id.tvcomfort);
            tv_seat=(TextView) itemView.findViewById(R.id.tvseat_count);
            tv_number=(TextView) itemView.findViewById(R.id.tvcarnumber);
            tv_car_type=(TextView) itemView.findViewById(R.id.tvcartype);

            car_img=(ImageView) itemView.findViewById(R.id.img_user_car);
        }
    }
}
