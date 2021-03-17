package online_learning_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class menu extends JMenuBar implements ActionListener, MenuListener {
    JMenu line_button, circle_button, rect_button, pen_menu, circle_Pen, square_Pen, eraser;

    public enum status {line, circle, rectangle, squarePen, circlePen}

    public static status draw_status;// = status.circle;

    public menu() {
        line_button = new JMenu("Line");
        circle_button = new JMenu("Circle");
        rect_button = new JMenu("Rectangle");
        pen_menu = new JMenu("Pen");
        circle_Pen = new JMenu("Circular-Tip Pen");
        square_Pen = new JMenu("Square-Tip Pen");
        this.add(line_button);
        this.add(circle_button);
        this.add(rect_button);
        this.add(pen_menu);
        square_Pen.addMenuListener(this);
        circle_Pen.addMenuListener(this);
        pen_menu.add(square_Pen);
        pen_menu.add(circle_Pen);
        circle_button.addMenuListener(this);
        rect_button.addMenuListener(this);
        line_button.addMenuListener(this);
        square_Pen.addMenuListener(this);
        circle_Pen.addMenuListener(this);
    }

    public status getdraw_status() {
        return draw_status;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource().equals(line_button)) {
            draw_status = status.line;
        }
        if (e.getSource().equals(circle_button)) {
            draw_status = status.circle;
        }
        if (e.getSource().equals(rect_button)) {
            draw_status = status.rectangle;
        }
        if (e.getSource().equals(square_Pen)) {
            draw_status = status.squarePen;
        }
        if (e.getSource().equals(circle_Pen)) {
            draw_status = status.circlePen;
        }
    }

    // TODO Auto-generated method stub


    @Override
    public void menuDeselected(MenuEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void menuCanceled(MenuEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }


}