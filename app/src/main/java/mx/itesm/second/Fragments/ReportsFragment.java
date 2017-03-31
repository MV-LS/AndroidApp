package mx.itesm.second.Fragments;

import android.content.Context;
//import com.google.android.gms.maps.model.LatLng;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import mx.itesm.second.Models.Venta;
import mx.itesm.second.R;

public class ReportsFragment extends Fragment
{
    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug","Sep", "Oct", "Nov", "Dec",};
    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};

    private Unbinder unbinder;
    private List<Venta> ventas_por_mes;
    private OnReportsFragmentInteractionListener mListener;

    @BindView(R.id.chart_top) LineChartView chartTop;
    @BindView(R.id.chart_bottom) ColumnChartView chartBottom;

    private LineChartData lineData;
    private ColumnChartData columnData;

    public ReportsFragment() {}

    public static ReportsFragment newInstance()
    {
        ReportsFragment fragment = new ReportsFragment();
        return fragment;
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        ventas_por_mes = new ArrayList<>(12);
        loadData();

        return rootView;
    }
    private void loadData()
    {
        Calendar calendar = Calendar.getInstance();
        //TODO AGREGAR DATOS DUMMY A LA GRAFICA, AGREGAR UN MAPA
        //Hacer Request al server
            //On Response
                Date now = new Date();
                calendar.setTime(now);

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //Domingo = 1, Sabado = 7

                generateInitialLineData();
                generateColumnData();

    }
    private void generateColumnData()
    {
        int numSubcolumns = 1; //1 Columna por mes
        int numColumns = months.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (int i = 0; i < numColumns; ++i) //Iterar por cada mes
        {
            values = new ArrayList<SubcolumnValue>();

            for (int j = 0; j < numSubcolumns; ++j) //Generar aleatoriamente la altura de la barra
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));

            axisValues.add(new AxisValue(i).setLabel(months[i]));

            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2)); //3 letras por mes

        chartBottom.setColumnChartData(columnData); //Inicializara el chart

        // Set value touch listener that will trigger changes for chartTop.
        chartBottom.setOnValueTouchListener( new ValueTouchListener() );

        // Set selection mode to keep selected month column highlighted.
        chartBottom.setValueSelectionEnabled(true);

        chartBottom.setZoomType(ZoomType.HORIZONTAL);

        // chartBottom.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // SelectedValue sv = chartBottom.getSelectedValue();
        // if (!sv.isSet()) {
        // generateInitialLineData();
        // }
        //
        // }
        // });

    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */
    private void generateInitialLineData()
    {
        int numValues = 7;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i)
        {
            values.add(new PointValue(i, 0)); //Init 0
            axisValues.add(new AxisValue(i).setLabel(days[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        chartTop.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chartTop.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        Viewport v = new Viewport(0, 110, 6, 0);
        chartTop.setMaximumViewport(v);
        chartTop.setCurrentViewport(v);

        chartTop.setZoomType(ZoomType.HORIZONTAL);
    }

    private void generateLineData(int color, float range) {
        // Cancel last animation if not finished.
        chartTop.cancelDataAnimation();

        // Modify data targets
        Line line = lineData.getLines().get(0);// For this example there is always only one line.
        line.setColor(color);
        for (PointValue value : line.getValues())
        {
            // Change target only for Y value.
            value.setTarget(value.getX(), (float) Math.random() * range);
        }
        // Start new data animation with 300ms duration;
        chartTop.startDataAnimation(300);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value)
        {
            generateLineData(value.getColor(), 100);
        }

        @Override
        public void onValueDeselected()
        {
            generateLineData(ChartUtils.COLOR_GREEN, 0);
        }
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnReportsFragmentInteractionListener)
        {
            mListener = (OnReportsFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()  + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnReportsFragmentInteractionListener
    {
        void onReportsFragmentInteraction();
    }
}
