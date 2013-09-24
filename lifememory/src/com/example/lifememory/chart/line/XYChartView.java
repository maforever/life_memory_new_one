package com.example.lifememory.chart.line;

import java.util.ArrayList;

import com.example.lifememory.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class XYChartView extends View{
  
  private int mWidth;          //X�᳤��
  private int mHeight;        //Y�᳤��
  
  private int countX = 12;      //X�������
  private int countY = 8;        //Y�������
  
  private String unitX = "月";      //X�ᵥλ
  private String unitY = "元";    //Y�ᵥλ
  
  private String[] coordinateX;    //X�����
  
  private int maxDataY = 1;        //Y��������ֵ
  
  private int spaceLeft = 45;      //�����Ե�ľ���
  private int spaceBottom = 120;    //���±�Ե�ľ���
  private int spaceTop = 40;      //���ϱ�Ե�ľ���
  private int spaceRight = 10;    //���ұ�Ե�ľ���
  
  private int paintColor = 0;      //������ɫ
  private int countLine = 0;      //���������
  
  private int tagX = 1;
  
  private ArrayList<XYChartData> lineList;   //���б�
  
  /*
     * �Զ���ؼ�һ��д����췽�� CoordinatesView(Context context)����javaӲ���봴���ؼ�
     * �����Ҫ���Լ��Ŀؼ��ܹ�ͨ��xml�����ͱ����е�2���췽�� CoordinatesView(Context context,
     * AttributeSet attrs) ��Ϊ��ܻ��Զ����þ���AttributeSet���������췽��������̳���View�Ŀؼ�
     */
  public XYChartView(Context context) {
    super(context, null);
    lineList = new ArrayList<XYChartData>();
    }

  public XYChartView(Context context, AttributeSet attrs) {
    super(context, attrs);
    lineList = new ArrayList<XYChartData>();
    }
  
  //��ȡX��������
  public int getCountX() {
    return countX;
  }

  //����X��������
  public void setCountX(int countX) {
    this.countX = countX;
  }

  //��ȡY��������
  public int getCountY() {
    return countY;
  }

  //����Y��������
  public void setCountY(int countY) {
    this.countY = countY;
  }

  //����X����ᵥλ
  public String getUnitX() {
    return unitX;
  }

  //����X����ᵥλ
  public void setUnitX(String unitX) {
    this.unitX = unitX;
  }
  
  //����X����ᵥλ
  public String getUnitY() {
    return unitY;
  }

  //����X����ᵥλ
  public void setUnitY(String unitY) {
    this.unitY = unitY;
  }

  
  //����Yֵ���ֵ
  public void findMaxDataY() {
    
    for(int j = 0; j <lineList.size(); j++){
      for(int i = 0; i < lineList.get(j).getCoordinateY().length; i++){
        if(maxDataY < lineList.get(j).getCoordinateY()[i])
          maxDataY = (int) lineList.get(j).getCoordinateY()[i];
        
      }  
    }
    while(maxDataY % (countY*10) != 0){
      maxDataY ++ ;
    }

  }
  
  public int getPaintColor() {
    return paintColor;
  }

  public void setPaintColor(int paintColor) {
    this.paintColor = paintColor;
  }

  /*
     * �ؼ��������֮������ʾ֮ǰ���������������ʱ���Ի�ȡ�ؼ��Ĵ�С ���õ�ԭ�����ĵ㡣
     */
    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
      mWidth = width - spaceLeft - spaceRight;          //���
      mHeight = height - spaceTop - spaceBottom;        //�߶�
           
        super.onSizeChanged(width, height, oldWidth, oldHeight);
    }
  
  /*
     * �Զ���ؼ�һ�㶼������onDraw(Canvas canvas)������������Լ���Ҫ��ͼ��
  */
    @Override
    public void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      this.setBackgroundResource(R.drawable.mymoney_bg);
        Paint paint = new Paint();
        paint.setStrokeWidth(8);
        paint.setColor(Color.BLACK);

//        dataY = new int[]{20, 84, 56, 70, 130, 125, 10, 40, 60, 45, 77, 12, 145};
        
//        coordinateX = new String[]{"1", "hello", "3", "4", "5", "6",
//            "10/2012", "10/2012", "10/2012", "10/2012", "10/2012", "10/2012"  };
        
//        coordinateY = new int[]{0, 20, 40, 60, 80, 100, 120, 140};
        
        
        // �������
        if (canvas != null) {
          //������ɫ
//          canvas.drawColor(Color.BLUE);
          canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mymoney_bg), 0, 0, paint);
            // ��ֱ��
            canvas.drawLine(spaceLeft, mHeight + spaceTop, mWidth + spaceLeft, mHeight +  spaceTop, paint);
            canvas.drawLine(spaceLeft, mHeight + spaceTop, spaceLeft, spaceTop , paint);
            
            // ��X���ͷ
            int x = mWidth + spaceLeft, y = mHeight + spaceTop;
            drawTriangle(canvas, new Point(x, y), new Point(x - 10, y - 5),
                new Point(x - 10, y + 5));
//            paint.setTextSize(20);
//            canvas.drawText(unitX, x - 15, y  18, paint);
           
            // ��Y���ͷ
            x = spaceLeft;
            y = spaceTop;
            drawTriangle(canvas, new Point(x, y), new Point(x - 5, y + 10),
                new Point(x + 5, y + 10));
            paint.setTextSize(20);
            canvas.drawText(unitY, x + 12, y + 15, paint);
            
            // �������̶���
            int pieceX = mWidth / countX, pieceY = mHeight / countY;
            drawBase(canvas, pieceX, pieceY);
            for(int i = 0; i < countLine; i++){
              drawLine(canvas, pieceX, lineList.get(i));
            }
        }
        
       
        

//        if (canvas != null) {
//          /*
//             * TODO ����� �����ⲿ��Ҫ��������ϻ�����ݣ�����������л���
//             */
//            canvas.drawCircle(po.x  2 * pa.x, po.y - 2 * pa.y, 2, paint);
//            canvas.drawCircle(po.x  2 * pb.x, po.y - 2 * pb.y, 2, paint);
//            canvas.drawLine(po.x  2 * pa.x, po.y - 2 * pa.y, po.x  2 * pb.x,
//                    po.y - 2 * pb.y, paint);
//            // canvas.drawPoint(pa.xpo.x, po.y-pa.y, paint);
//        }

    }
    
    /**
     * 
     * @Title: drawLine 
     * @Description: TODO(����) 
     * @param @param canvas
     * @param @param pieceX
     * @param @param line    �趨�ļ� 
     * @return void    �������� 
     * @throws
     */
    private void drawLine(Canvas canvas, int pieceX, XYChartData line){
      Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setColor(line.getPaintColor());  
        
        Paint paint1 = new Paint();
        paint1.setStrokeWidth(4);
        paint1.setColor(Color.BLACK); 
        paint1.setTextSize(30);
        canvas.drawLine(tagX, mHeight + spaceTop + 60, tagX + 15, mHeight + spaceTop + 60, paint);
        canvas.drawText(line.getLineName(), tagX + 15, mHeight + spaceTop + 70, paint1);
        tagX += 70;
        
        int[] y = line.getCoordinateY();
                 
        paint1.setTextSize(20);
        paint.setStrokeWidth(4);
      for(int i = 0; i < countX; i++){
        
        Log.v("lineY", y[i]+"");
        canvas.drawCircle(spaceLeft + pieceX * (i), mHeight + spaceTop - mHeight * y[i] / maxDataY, 4, paint);
        canvas.drawText(y[i]+"", spaceLeft + pieceX *(i), mHeight + spaceTop - mHeight * y[i] / maxDataY - 10, paint1);
          
          if(i < countX - 1){
            canvas.drawLine(spaceLeft + pieceX * (i), mHeight + spaceTop - mHeight * y[i] / maxDataY, 
                spaceLeft + pieceX * (i + 1), mHeight + spaceTop - mHeight * y[i + 1] / maxDataY, paint);
          }
          }
    }
    
    /**
     * 
     * @Title: drawBase 
     * @Description: TODO(���������) 
     * @param @param canvas
     * @param @param pieceX  
     * @param @param pieceY    
     * @return void    �������� 
     * @throws
     */
    private void drawBase(Canvas canvas, int pieceX, int pieceY){
      Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);
        
        //��X��̶�
        for(int i = 0; i < countX; i++){
          canvas.drawLine(spaceLeft + pieceX * i, mHeight+ spaceTop - 5, 
              spaceLeft + pieceX * i, mHeight+ spaceTop + 5 , paint);  
          
          paint.setTextAlign(Paint.Align.CENTER);
          canvas.drawText(coordinateX[i], spaceLeft+  pieceX *(i), mHeight + spaceTop + 5 + 18, paint);      
        }
        
        //��Y��̶�
        paint.setTextSize(25);
        paint.setStrokeWidth(3);
        for(int j = 0; j < countY; j++){
          paint.setColor(Color.WHITE);
          canvas.drawLine(spaceLeft - 5, mHeight + spaceTop - pieceY * j, 
              spaceLeft+  5, mHeight + spaceTop - pieceY * j , paint);
          
          paint.setTextAlign(Paint.Align.CENTER);
          
//          canvas.drawText(coordinateY[j]"", spaceLeft - 18 , mHeight  spaceTop - pieceY * j  5, paint);
            paint.setColor(Color.RED);
          canvas.drawText(maxDataY * j / countY + "", spaceLeft , mHeight + spaceTop - pieceY * j + 5, paint);
        }
    }
    
    /**
     * ������� ���ڻ������ļ�ͷ
     */
    private void drawTriangle(Canvas canvas, Point p1, Point p2, Point p3) {
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        // �����������
        canvas.drawPath(path, paint);
    }

  /**
   * 
   * @Title: addLine 
   * @Description: TODO(������һ�仰��������������) 
   * @param @param coordinateX
   * @param @param coordinateY
   * @param @param dataY
   * @param @param color    �趨�ļ� 
   * @return void    �������� 
   * @throws
   */
  public void addLine(String lineName, String[] coordinateX, int[] dataY, int billType){
    int paintColor = 0;
    countX = coordinateX.length;
    
    if(billType == 1) {
      paintColor = Color.RED;
    }else if(billType == 2){
      paintColor = Color.GREEN;
    }
    
    
    
    countLine++;  
    lineList.add(new XYChartData(lineName, coordinateX, dataY, paintColor));
        
    findMaxDataY();    //�ҳ����������ֵ
    this.coordinateX = coordinateX;
    
    
  }
    
}
