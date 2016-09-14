import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;


public class OutputWindow {
	private JFrame j;
	private JTextArea t;
	private JScrollPane s;
	private int lwl = 0;
	DefaultCaret caret;
	public OutputWindow(JFrame j){
		this.s = new JScrollPane();
		this.t = new JTextArea();
		caret = (DefaultCaret)t.getCaret();
		this.j = j;
		this.s.setViewportView(this.t);
		this.j.add(s);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		t.setEditable(false);
		t.getCaret().setVisible(false);
		this.j.setVisible(true);
	}
	
	public void write(String s){
		t.append(s+"\n");
		lwl = (s+"\n").length();
		t.setCaretPosition(t.getDocument().getLength());
	}
	
	public void write(int i){
		t.append(Integer.toString(i)+"\n");
		lwl = (Integer.toString(i)+"\n").length();
		t.setCaretPosition(t.getDocument().getLength());
	}
	
	public void removeLastWrite(){
		t.setText(t.getText().substring(0, t.getText().length()-lwl));
	}
}
