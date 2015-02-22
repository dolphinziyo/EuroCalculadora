package com.dolphin.android.eurocalculadora;

/*
*   Creado por dolphinziyo el 07/03/2012
*   http://tecnogame.org
*   http://twitter.com/dolphinziyo
*/

import java.text.DecimalFormat;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EuroCalculadoraActivity extends Activity implements OnClickListener{
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnComa,btnCe,btnPe,btnEp;
	EditText entrada;
	TextView moneda;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		// Ocultamos la barra de título de la aplicación
        setContentView(R.layout.main);
        
        // Definimos lo que serán los botones de la aplicación (números)
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btn0 = (Button)findViewById(R.id.btn0);
        
        // Definimos lo que serán los botones de la aplicación (símbolos)
        btnComa = (Button)findViewById(R.id.btnPunto);
        btnCe = (Button)findViewById(R.id.btnLimpiar);
        btnPe = (Button)findViewById(R.id.btnPe);
        btnEp = (Button)findViewById(R.id.btnEp);
        
        entrada = (EditText)findViewById(R.id.texto);
        entrada.setInputType(InputType.TYPE_NULL);		// Evitamos que se muestre el teclado del sistema al hacer el click en el campo de texto
        
        // Permitimos sólo introducir números y la "," o el "." para indicar los decimales
        DigitsKeyListener MyDigitKeyListener =
        		new DigitsKeyListener(true, true); 		
        		entrada.setKeyListener( MyDigitKeyListener );
        
        moneda = (TextView)findViewById(R.id.moneda);
        
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnCe.setOnClickListener(this);
        
        btnComa.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String texto = entrada.getText().toString();
				char[] arrTexto = texto.toCharArray();			// Creamos un array con los datos introducidos
				boolean encontrado = false;						// Variable bandera por si el punto ya existe
				int i = 0;
				while(i < arrTexto.length && !encontrado){	
					if(arrTexto[i]=='.'|| arrTexto[i]==','){	// Buscamos el punto o la coma en los datos introducidos en el campo de texto
						encontrado=true;
					}else{
						encontrado=false;
					}
					i++;
				}		
				if(!encontrado && !entrada.getText().toString().equals("")){		// Si no hay ningún punto ni coma y no está vacío, ponemos el punto
						entrada.setText(entrada.getText()+ ".");
				}
				entrada.setSelection(entrada.getText().length());					// Esto ubica el cursor en la última posición
			}
        });
        
        // Al pulsar el botón de conversión de pesetas a euros
        btnPe.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(!entrada.getText().toString().equals("")){
					// TODO Auto-generated method stub
					if(!entrada.getText().toString().equals("")){	
						moneda.setText("");
					}
					String strCantidad = comprobarComa(entrada.getText().toString());		// Cogemos la cantidad introducida comprobando si hay una coma para sustituirla por un punto	
					Double douCantidad = Double.parseDouble(strCantidad);					
					String strResultado = formateaDecimal(douCantidad/166.386);				// Almacenamos el resultado formateándolo para mostrar decimales
					entrada.setText(strResultado);											// Mostramos el resultado	
					moneda.setText("€");													// Ponemos el símbolo del euro para indicar que se ha convertido a euros
				}
				entrada.setSelection(entrada.getText().length());
			}
        });
        
        // Al pulsar el botón de conversión de euros a pesetas
        btnEp.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!entrada.getText().toString().equals("")){
					if(!entrada.getText().toString().equals("")){
						moneda.setText("");
					}
					String strCantidad = comprobarComa(entrada.getText().toString());		// Cogemos la cantidad introducida comprobando si hay una coma para sustituirla por un punto
					Double douCantidad = Double.parseDouble(strCantidad);					
					String strResultado = formateaDecimal(douCantidad*166.386);				// Almacenamos el resultado formateándolo para mostrar decimales
					entrada.setText(strResultado);											// Mostramos el resultado
					moneda.setText("P");													// Ponemos una "P" para indicar que se ha convertido a pesetas
				}
				entrada.setSelection(entrada.getText().length());
			}
        });
    }		// Fin del "onCreate"
    
    // Función encargada de mostrar los números con dos decimales
    private String formateaDecimal(double numero){
    	DecimalFormat df = new DecimalFormat("0.00");		// Mostramos sólo dos decimales
    	String numeroFormateado = df.format(numero);		// Formateamos el número recibido
    	return numeroFormateado;							// Devolvemos el número formateado
    }
    
    // Función encargada de sustituir la coma por un punto
    private String comprobarComa(String texto){
		char[] arrTexto = texto.toCharArray();				
		String strTexto;
		int i = 0;
		while(i < arrTexto.length){
			if(arrTexto[i]==','){							// Si encontramos una coma en la cifra recibida
				arrTexto[i]='.';							// La sustituimos por un punto para poder realizar la operación
			}
			i++;
		}		
		strTexto = new String(arrTexto);
		return strTexto;
    }
    
    // Función encargada de introducir los números en el campo de texto
    private void introducirNumero(String numero){				
		if(!entrada.getText().toString().equals("")){			
			moneda.setText("");
		}
		entrada.setText(entrada.getText() + numero);			// Se introduce en la caja de texto lo que haya más el nuevo número introducido
		entrada.setSelection(entrada.getText().length());		
    }
    
    // Función encargada de limpiar el campo de texto y la etiqueta "moneda"
    private void limpiar(){
		entrada.setText("");
		moneda.setText("");
    }
    
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(this.findViewById(R.id.btn0))) {
			introducirNumero("0");
		}else if(v.equals(this.findViewById(R.id.btn1))){
			introducirNumero("1");
		}else if(v.equals(this.findViewById(R.id.btn2))){
			introducirNumero("2");
		}else if(v.equals(this.findViewById(R.id.btn3))){
			introducirNumero("3");
		}else if(v.equals(this.findViewById(R.id.btn4))){
			introducirNumero("4");
		}else if(v.equals(this.findViewById(R.id.btn5))){
			introducirNumero("5");
		}else if(v.equals(this.findViewById(R.id.btn6))){
			introducirNumero("6");
		}else if(v.equals(this.findViewById(R.id.btn7))){
			introducirNumero("7");
		}else if(v.equals(this.findViewById(R.id.btn8))){
			introducirNumero("8");
		}else if(v.equals(this.findViewById(R.id.btn9))){
			introducirNumero("9");
		}else if(v.equals(this.findViewById(R.id.btnLimpiar))){
			limpiar();
		}
	}
}		// Fin de la aplicación