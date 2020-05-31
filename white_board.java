package online_learing_system;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.*;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.*;

import online_learing_system.menu.status;


public class white_board extends JPanel implements Runnable {
    // constructor with port
    private Socket socket = new Socket("", 5001);
    private DataInputStream input = null;
    private DataOutputStream out = null;


    private final ArrayList<Point> line_coordinates = new ArrayList<>();
    private final ArrayList<Point> circle_coordinates = new ArrayList<>();
    private final ArrayList<Point> rectangle_coordinates = new ArrayList<>();
    private final ArrayList<Point> line_start = new ArrayList<>();
    private final ArrayList<Point> circle_start = new ArrayList<>();
    private final ArrayList<Point> rectangle_start = new ArrayList<>();

    private final ArrayList<Point> circle_Pen = new ArrayList<>();
    private final ArrayList<Point> square_Pen = new ArrayList<>();
    private final ArrayList<Point> eraser_Pen = new ArrayList<>();

    int beginX_Line = 0;
    int endX_Line = 0;
    int endY_Line = 0;
    int beginY_Line = 0;
    int beginX_Circle = 0;
    int endX_Circle = 0;
    int endY_Circle = 0;
    int beginY_Circle = 0;

    int beginX_Rectangle = 0;
    int endX_Rectangle = 0;
    int endY_Rectangle = 0;
    int beginY_Rectangle = 0;

    public ArrayList<String> outputToSend = new ArrayList<>();

    public white_board() throws IOException {
        menu g = new menu();
        addMouseListener(
                new MouseAdapter() {
                    public void mousePressed(MouseEvent event) {
                        if (g.getdraw_status() == status.line) {
                            beginX_Line = endX_Line = event.getX();
                            beginY_Line = endY_Line = event.getY();
                            Point begin = new Point();
                            begin.x = beginX_Line;
                            begin.y = beginY_Line;
                            line_start.add(begin);
                            outputToSend.removeAll(outputToSend);
                            outputToSend.add("DrawLine");
                            outputToSend.add(""+beginX_Line);
                            outputToSend.add(""+beginY_Line);
                            repaint();
                        }

                        if (g.getdraw_status() == status.circle) {
                            beginX_Circle = endX_Circle = event.getX();
                            beginY_Circle = endY_Circle = event.getY();
                            Point begin = new Point();
                            begin.x = beginX_Circle;
                            begin.y = beginY_Circle;
                            circle_start.add(begin);
                            outputToSend.removeAll(outputToSend);
                            outputToSend.add("DrawCircle");
                            outputToSend.add(""+beginX_Circle);
                            outputToSend.add(""+beginY_Circle);
                            repaint();
                        }

                        if (g.getdraw_status() == status.rectangle) {
                            beginX_Rectangle = endX_Rectangle = event.getX();
                            beginY_Rectangle = endY_Rectangle = event.getY();
                            Point begin = new Point();
                            begin.x = beginX_Rectangle;
                            begin.y = beginY_Rectangle;
                            rectangle_start.add(begin);
                            outputToSend.removeAll(outputToSend);
                            outputToSend.add("DrawRectangle");
                            outputToSend.add(""+beginX_Rectangle);
                            outputToSend.add(""+beginY_Rectangle);
                            repaint();
                        }
                    }

                    public void mouseReleased(MouseEvent event) {
                        if (g.getdraw_status() == status.line) {
                            endX_Line = event.getX();
                            endY_Line = event.getY();
                            Point end = new Point();
                            end.x = endX_Line;
                            end.y = endY_Line;
                            line_coordinates.add(end);
                            outputToSend.add(""+endX_Line);
                            outputToSend.add(""+endY_Line);
                            try {
                                this.send(outputToSend);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            repaint();
                        }

                        if (g.getdraw_status() == status.circle) {
                            endX_Circle = event.getX();
                            endY_Circle = event.getY();
                            Point end = new Point();
                            end.x = endX_Circle;
                            end.y = endY_Circle;
                            circle_coordinates.add(end);
                            outputToSend.add(""+endX_Circle);
                            outputToSend.add(""+endY_Circle);
                            try {
                                this.send(outputToSend);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            repaint();
                        }

                        if (g.getdraw_status() == status.rectangle) {
                            endX_Rectangle = event.getX();
                            endY_Rectangle = event.getY();
                            Point end = new Point();
                            end.x = endX_Rectangle;
                            end.y = endY_Rectangle;
                            rectangle_coordinates.add(end);
                            outputToSend.add(""+endX_Rectangle);
                            outputToSend.add(""+endY_Rectangle);
                            try {
                                this.send(outputToSend);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            repaint();
                        }
                    }

                    public void send(ArrayList<String> outputToSend) throws IOException {
                            System.out.println("Connected");
                            OutputStream outputStream = socket.getOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                            objectOutputStream.writeObject(outputToSend);

                        return;

                    }

                });
        addMouseMotionListener(
                new MouseMotionAdapter() {

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (g.getdraw_status() == status.line) {
                            endX_Line = e.getX();
                            endY_Line = e.getY();
                            repaint();
                        }
                        if (g.getdraw_status() == status.circle) {
                            endX_Circle = e.getX();
                            endY_Circle = e.getY();
                            repaint();
                        }

                        if (g.getdraw_status() == status.rectangle) {
                            endX_Rectangle = e.getX();
                            endY_Rectangle = e.getY();
                            repaint();
                        }

                        if(g.getdraw_status() == status.circlePen)
                        {
                            outputToSend.add("CirclePen");
                            circle_Pen.add(e.getPoint());
                            outputToSend.add(""+ e.getX());
                            outputToSend.add(""+e.getY());
                            try {
                                this.send(outputToSend);
                            } catch (IOException i) {
                                i.printStackTrace();
                            }
                            outputToSend.removeAll(outputToSend);
                            repaint();
                        }

                        if(g.getdraw_status() == status.squarePen)
                        {
                            outputToSend.add("SquarePen");
                            square_Pen.add(e.getPoint());
                            outputToSend.add(""+ e.getX());
                            outputToSend.add(""+e.getY());
                            try {
                                this.send(outputToSend);
                            } catch (IOException i) {
                                i.printStackTrace();
                            }
                            outputToSend.removeAll(outputToSend);
                            repaint();
                        }


                        if(g.getdraw_status() == status.eraserPen)
                        {
//                            line_coordinates.removeAll(line_coordinates);
//                            circle_coordinates.removeAll(circle_coordinates);
//                            rectangle_coordinates.removeAll(rectangle_coordinates);
//                            line_start.removeAll(line_start);
//                            circle_start.removeAll(line_start);
//                            rectangle_start.removeAll(rectangle_start);
//
//                            circle_Pen.removeAll(circle_Pen);
//                            square_Pen.removeAll(square_Pen);
//                            repaint();
                            //eraser();
                            repaint();
                        }

                    }

                    public void send(ArrayList<String> outputToSend) throws IOException {
                        System.out.println("Connected");
                        OutputStream outputStream = socket.getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(outputToSend);

                        return;
                    }


                });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //LINE DRAWING
        for (int i = 0; i < line_coordinates.size(); i++) {
            g.drawLine(line_start.get(i).x, line_start.get(i).y, line_coordinates.get(i).x, line_coordinates.get(i).y);
        }
        g.drawLine(beginX_Line, beginY_Line, endX_Line, endY_Line);


        // CIRCLE DRAWING
        if (beginX_Circle > endX_Circle && beginY_Circle > endY_Circle)
            g.drawOval(endX_Circle, endY_Circle, beginX_Circle - endX_Circle, beginY_Circle - endY_Circle);
        if (beginX_Circle > endX_Circle && beginY_Circle < endY_Circle)
            g.drawOval(endX_Circle, beginY_Circle, beginX_Circle - endX_Circle, endY_Circle - beginY_Circle);
        if (beginX_Circle < endX_Circle && beginY_Circle > endY_Circle)
            g.drawOval(beginX_Circle, endY_Circle, endX_Circle - beginX_Circle, beginY_Circle - endY_Circle);
        else
            g.drawOval(beginX_Circle, beginY_Circle, endX_Circle - beginX_Circle, endY_Circle - beginY_Circle);
        for (int i = 0; i < circle_coordinates.size(); i++) {
            if (circle_start.get(i).x > circle_coordinates.get(i).x && circle_start.get(i).y > circle_coordinates.get(i).y)
                g.drawOval(circle_coordinates.get(i).x, circle_coordinates.get(i).y, circle_start.get(i).x - circle_coordinates.get(i).x, circle_start.get(i).y - circle_coordinates.get(i).y);
            if (circle_start.get(i).x > circle_coordinates.get(i).x && circle_start.get(i).y < circle_coordinates.get(i).y)
                g.drawOval(circle_coordinates.get(i).x, circle_start.get(i).y, circle_start.get(i).x - circle_coordinates.get(i).x, circle_coordinates.get(i).y - circle_start.get(i).y);
            if (circle_start.get(i).x < circle_coordinates.get(i).x && circle_start.get(i).y > circle_coordinates.get(i).y)
                g.drawOval(circle_start.get(i).x, circle_coordinates.get(i).y, circle_coordinates.get(i).x - beginX_Circle, circle_start.get(i).y - circle_coordinates.get(i).y);
            else
                g.drawOval(circle_start.get(i).x, circle_start.get(i).y, circle_coordinates.get(i).x - circle_start.get(i).x, circle_coordinates.get(i).y - circle_start.get(i).y);
        }


        // RECTANGLE DRAWING
        if (beginX_Rectangle > endX_Rectangle && beginY_Rectangle > endY_Rectangle)
            g.drawRect(endX_Rectangle, endY_Rectangle, beginX_Rectangle - endX_Rectangle, beginY_Rectangle - endY_Rectangle);
        if (beginX_Rectangle > endX_Rectangle && beginY_Rectangle < endY_Rectangle)
            g.drawRect(endX_Rectangle, beginY_Rectangle, beginX_Rectangle - endX_Rectangle, endY_Rectangle - beginY_Rectangle);
        if (beginX_Rectangle < endX_Rectangle && beginY_Rectangle > endY_Rectangle)
            g.drawRect(beginX_Rectangle, endY_Rectangle, endX_Rectangle - beginX_Rectangle, beginY_Rectangle - endY_Rectangle);
        else
            g.drawRect(beginX_Rectangle, beginY_Rectangle, endX_Rectangle - beginX_Rectangle, endY_Rectangle - beginY_Rectangle);
        for (int i = 0; i < rectangle_coordinates.size(); i++) {
            if (rectangle_start.get(i).x > rectangle_coordinates.get(i).x && rectangle_start.get(i).y > rectangle_coordinates.get(i).y)
                g.drawRect(rectangle_coordinates.get(i).x, rectangle_coordinates.get(i).y, rectangle_start.get(i).x - rectangle_coordinates.get(i).x, rectangle_start.get(i).y - rectangle_coordinates.get(i).y);
            if (rectangle_start.get(i).x > rectangle_coordinates.get(i).x && rectangle_start.get(i).y < rectangle_coordinates.get(i).y)
                g.drawRect(rectangle_coordinates.get(i).x, rectangle_start.get(i).y, rectangle_start.get(i).x - rectangle_coordinates.get(i).x, rectangle_coordinates.get(i).y - rectangle_start.get(i).y);
            if (rectangle_start.get(i).x < rectangle_coordinates.get(i).x && rectangle_start.get(i).y > rectangle_coordinates.get(i).y)
                g.drawRect(rectangle_start.get(i).x, rectangle_coordinates.get(i).y, rectangle_coordinates.get(i).x - beginX_Rectangle, rectangle_start.get(i).y - rectangle_coordinates.get(i).y);
            else
                g.drawRect(rectangle_start.get(i).x, rectangle_start.get(i).y, rectangle_coordinates.get(i).x - rectangle_start.get(i).x, rectangle_coordinates.get(i).y - rectangle_start.get(i).y);
        }

        for(Point point : circle_Pen)
        {
            g.fillOval(point.x, point.y, 100, 100);
        }
        for(Point point : square_Pen)
        {
            g.fillRect(point.x, point.y, 100, 100);
        }

        //System.out.println(outputToSend);
    }


    @Override
    public void run() {
        white_board runner = this;
    }
}