package cn.jaychen.jaycustomviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jaychen.jaycustomviews.bean.Page;

import static cn.jaychen.jaycustomviews.utils.UiUtil.dpToPx;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            private List<Page> pages = new ArrayList<>();

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) dpToPx(50)));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setGravity(Gravity.CENTER);

                return new ItemViewHolder(textView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


                ((ItemViewHolder) holder).bindData(getPages().get(position));

            }

            private List<Page> getPages() {

                if (pages.size() == 0) {
                    Page page = new Page("Charts", ChartActivity.class);
                    pages.add(page);
                }

                return pages;
            }

            @Override
            public int getItemCount() {
                return getPages().size();
            }

            class ItemViewHolder extends RecyclerView.ViewHolder {

                ItemViewHolder(View itemView) {
                    super(itemView);
                }

                void bindData(final Page page) {

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, page.getClazz());
                            startActivity(intent);
                        }
                    });
                    TextView textView = (TextView) itemView;
                    textView.setText(page.getTitle());
                }
            }
        });


    }


}
