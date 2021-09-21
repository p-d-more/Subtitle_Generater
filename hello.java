package com.mycompany.hello;
import java.io.File;
//import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import java.awt.Container;
import java.io.IOException;
import javax.sound.sampled.*;
public class hello {     
   static  AudioInputStream audio;
    static Clip clip;
public static void main(String[] args) throws Exception 
    {  
       
       JFrame  f=new JFrame();
        f.setVisible(true);
        f.setSize(1000,1000);
       f.setResizable(false);
        f.setLayout(null);
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

     JPanel px=new JPanel();
     ImageIcon img=new ImageIcon("bg2.jpg");
      Image img1=img.getImage();
        Image img2=img1.getScaledInstance(1000, 1000,Image.SCALE_SMOOTH);
        ImageIcon img3=new ImageIcon(img2);
       
     px.setBounds(0,0,1000,1000);
     JLabel lx=new JLabel(img3);
        Container c=f.getContentPane();
        lx.setBounds(0,0,1000,1000);
        JPanel p1=new JPanel();
        lx.add(p1);
        p1.setBounds(0,0,1000,100); 
        
        JLabel jb1=new JLabel(new ImageIcon("giphy.gif"));
        JLabel jb2=new JLabel("AUDIO TO TEXT");
        p1.setBackground(new Color(0,0,0,50));
        p1.setForeground(Color.red);
        p1.add(jb1);
        jb1.setBounds(0,0,1000,180);
        jb1.add(jb2);        
       jb2.setBounds(110,2,300,130);
       jb2.setForeground(Color.WHITE);
        jb2.setFont(new Font("serif",Font.BOLD,35));
        JLabel jl1= new JLabel("Your Audio");
        jl1.setBounds(100,300,200,70);
        jl1.setFont(new Font("serif",Font.BOLD,33));
        jl1.setForeground(Color.WHITE);
        TextField t1=new TextField();
        t1.setBounds(350,300,300,70);
        t1.setFont(new Font("serif",Font.BOLD,24));
        lx.add(jl1);
        c.add(t1);
        JLabel jl2= new JLabel("Your Subtitles");
        jl2.setBounds(100,550,220,70);
        jl2.setFont(new Font("serif",Font.BOLD,33));
        jl2.setForeground(Color.WHITE);
        lx.add(jl2);
        JTextArea ta=new JTextArea();
               lx.add(ta);
        JButton b1=new JButton("play");
        b1.setFont(new Font("serif",Font.BOLD,24));
        b1.setBounds(300,800,150,50);
        lx.add(b1);
       b1.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent eq)
           {
               try
               {
               String str=t1.getText();
               audio=AudioSystem.getAudioInputStream(new File(str).getAbsoluteFile());
               clip=AudioSystem.getClip();
               clip.open(audio);
               clip.start();
           }
               catch(Exception ex){}
           }
       });
       JButton b2=new JButton("stop");
       b1.setFont(new Font("serif",Font.BOLD,24));
        b2.setBounds(600,800,150,50);
        lx.add(b2);
        b2.setFont(new Font("serif",Font.BOLD,24));
       b2.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent eq)
           {
               
              clip.stop();
               clip.close();
           }
             
       });
      
        
        ta.setBounds(350,500,600,200);
         // ta.setBackground(new Color(250,250,250,20));
        ta.setFont(new Font("serif",Font.BOLD,25));
        px.add(lx);
        c.add(px);

        // b.setFont(new Font("serif",Font.BOLD,20));
             Configuration con = new Configuration();

        con.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        con.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        con.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        SpeechResult result;   
        StreamSpeechRecognizer recognizer = null;   

           FileDialog fd=new FileDialog(f,"",FileDialog.LOAD);
           fd.setSize(300,300);
           fd.setVisible(true);
           String name=fd.getFile();
           t1.setText(name);
           try{
             recognizer = new StreamSpeechRecognizer(con);
           }
           catch(IOException e1)
           {
               
           }
        //String path=name+".wav";
         try
         {
	InputStream stream = new FileInputStream(new File(name));
        recognizer.startRecognition(stream);
         }
         catch(IOException e2)
         {
             
         }
        
        while ((result = recognizer.getResult()) != null) {
	    
            ta.setText(result.getHypothesis());
     }
        f.dispose();
           
                
	recognizer.stopRecognition();
        
       
            }
    }
