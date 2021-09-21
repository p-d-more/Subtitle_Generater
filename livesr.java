/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hello;

/**
 *
 * @author Pranjali
 */
import java.io.*;
import java.util.List;
import java.util.concurrent.*;
import javax.sound.sampled.*;
import edu.cmu.sphinx.api.*;
import edu.cmu.sphinx.result.WordResult;
public class livesr {	
    private LiveSpeechRecognizer rec;
    private String speechRResult;
    private boolean ignoreSpeechRResults = false, speechRecognizerThreadRunning = false, resourcesThreadRunning;
    private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);
    	public static void main(String[] args) {
        Configuration con = new Configuration();
        con.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        con.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        con.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
	new livesr(con);
	}
        public livesr(Configuration con) {
            try {
                rec = new LiveSpeechRecognizer(con);
		} catch (IOException ex) {
       		}
        startResourcesThread();
        startSpeechRecognition();
	}
	public synchronized void startSpeechRecognition() {
            eventsExecutorService.submit(() -> {
            speechRecognizerThreadRunning = true;   
            ignoreSpeechRResults = false;
            rec.startRecognition(true);	
	    System.out.println("you can speak......");
		try {
                    while (speechRecognizerThreadRunning) {
			SpeechResult speechResult = rec.getResult();
			if (!ignoreSpeechRResults) {
                            if (speechResult == null)
				System.out.println("I can't understand what you said.\n");
                             else {
				speechRResult = speechResult.getHypothesis();
				System.out.println("You said: [" + speechRResult + "]\n");
				makeDecision(speechRResult, speechResult.getWords());
                                }
			}
                        }
                    } catch (Exception ex) {
			speechRecognizerThreadRunning = false;
			}
			System.out.println("SpeechThread has exited...");
			});
	}
	public synchronized void stopIgnoreSpeechRecognitionResults() {
		ignoreSpeechRResults = false;
	}
        public synchronized void ignoreSpeechRecognitionResults() {	
		ignoreSpeechRResults = true;
	}
	public void startResourcesThread() {
			eventsExecutorService.submit(() -> {
				try {
					resourcesThreadRunning = true;
					while (true) {
						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
                                                    System.out.println("Microphone is not available.\n");
						Thread.sleep(300);
					}
				} catch (InterruptedException ex) {
					resourcesThreadRunning = false;
				}
			});
	}
	public void makeDecision(String speech , List<WordResult> speechWords) {
		System.out.println(speech);
		}
	public boolean getIgnoreSpeechRecognitionResults() {
		return ignoreSpeechRResults;
	}
	public boolean getSpeechRecognizerThreadRunning() {
		return speechRecognizerThreadRunning;
	}
}

