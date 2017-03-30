//package elemulator;

import java.util.ArrayList;

//import br.edu.insper.elemulator.model.RAM;

//public class Screen extends View {
public class Screen {
    //Paint paint;
    int y = 0;
    ArrayList<Integer> x;


    //public Screen(Context context) {
    public Screen() {
        //super(context);
        //this.paint = new Paint();
        x = new ArrayList<Integer>();
        //paint.setStyle(Paint.Style.FILL);

    }


    //protected void onDraw(Canvas canvas) {
    protected void onDraw() {
        //canvas.drawColor(Color.WHITE);

        for (int i : this.x) {
            //paint.setColor(Color.BLACK);
            //canvas.drawCircle(i, y, 1, paint);
        }

    }

    protected void markPixel(ArrayList<Integer> x, int y) {
        this.x = x;
        this.y = y;
    }

}