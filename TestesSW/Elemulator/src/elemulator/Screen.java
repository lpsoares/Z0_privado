/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

package elemulator;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Screen {

    boolean image[][];
    int[] resolution = null;

    public Screen(int[] resolution ) {
        this.resolution = resolution;
        image = new boolean[resolution[1]][resolution[0]]; //por padrao tudo falso
    }

    public int[] getResolution() {
        return(resolution);
    }

    void saveDisplay(String arquivo) {
    
        try{

            //specify the name of the output..
            FileWriter fstream = new FileWriter(arquivo);

            //we create a new BufferedWriter
            BufferedWriter out = new BufferedWriter(fstream);
     
            //we add the header, 128 128 is the width-height and 63 is the max value-1 of ur data
            out.write("P1\n# CREATOR:Luciano\n"+String.valueOf(resolution[0])+" "+String.valueOf(resolution[1])+"\n");

            //2 loops to read the 2d array
            for(int i = 0 ; i<image.length;i++)
                for(int j = 0 ; j<image[0].length;j++)
                    //we write in the output the value in the position ij of the array
                    out.write((image[i][j]?"1":"0")+" ");
            
            //we close the bufferedwritter
            out.close();
        } catch (Exception e){
            System.err.println("Error : " + e.getMessage());
        }

    }

    protected void markPixel(int x, int y, boolean[] register) {

        for(int i=0; i<register.length; i++) {
            image[x][y+i] = register[i];
        }

    }

}