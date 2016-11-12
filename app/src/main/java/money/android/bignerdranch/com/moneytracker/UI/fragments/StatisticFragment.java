package money.android.bignerdranch.com.moneytracker.UI.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.androidannotations.annotations.PreferenceScreen;

import java.util.ArrayList;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;
public class StatisticFragment extends Fragment{

    protected List<CategoryEntity> categoryEntityList ;
    protected List<ExpensesEntity> expensesEntities ;
    protected List<ExpensesEntity> entitiesOrderByOnDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistic_fragment, container, false);
        readAllCategories();
        PieChart pieChart = (PieChart)rootView.findViewById(R.id.pieChart);
        pieChart.setDrawHoleEnabled(true);
        setData(pieChart, 100);
        return rootView;
    }

    private void setData(PieChart pieChart, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < categoryEntityList.size(); i++) {
            List <ExpensesEntity> expensesEntityList = ExpensesEntity.selectSortCategory(categoryEntityList.get(i).getId());
            if (expensesEntityList.size() != 0)
            entries.add(new PieEntry((float) (expensesEntityList.size() * range) / expensesEntities.size(), categoryEntityList.get(i).getName()));

        }
        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.category));
        dataSet.setDrawValues(true);
// add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.COLORFUL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.LIBERTY_COLORS) {
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);
    }


    private void readAllCategories(){
        categoryEntityList = CategoryEntity.selectAll("");
        expensesEntities = ExpensesEntity.selectAll("");
        entitiesOrderByOnDate = ExpensesEntity.SelectSortExpensesOnDate();
    }

}
