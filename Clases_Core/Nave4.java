package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;



public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private float rotationDeg = 0f;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    private boolean tripleDisparoActivo = false;
    private boolean escudoActivo = false;
    private final float DURACION_ESCUDO = 10f; 
    private float tiempoEscudoActivo = 0f; 

    
    private float velocidadBase = 2f;
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);
    	spr.setOriginCenter();   

    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
        float x =  spr.getX();
        float y =  spr.getY();
        if (!herido) {
        	
	        // que se mueva con teclado
        	xVel = 0;
        	yVel = 0;
        	if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  xVel = -velocidadBase;
        	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xVel =  velocidadBase;
        	if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  yVel = -velocidadBase;
        	if (Gdx.input.isKeyPressed(Input.Keys.UP))    yVel =  velocidadBase;
        	
        	if (xVel != 0 || yVel != 0) {
        	    rotationDeg = MathUtils.atan2(yVel, xVel) * MathUtils.radiansToDegrees - 90f;
        	}
        	spr.setRotation(rotationDeg);
        	
	     /*   if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spr.setRotation(++rotacion);
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spr.setRotation(--rotacion);
	        
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	xVel -=Math.sin(Math.toRadians(rotacion));
	        	yVel +=Math.cos(Math.toRadians(rotacion));
	        	System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;    
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	        	xVel +=Math.sin(Math.toRadians(rotacion));
	        	yVel -=Math.cos(Math.toRadians(rotacion));
	        	     
	        }*/
	        
	        // que se mantenga dentro de los bordes de la ventana
	        if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth())
	        	xVel*=-1;
	        if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
	        	yVel*=-1;
	        
	        spr.setPosition(x+xVel, y+yVel);   
         
 		    spr.draw(batch);
        } else {
           spr.setX(spr.getX()+MathUtils.random(-2,2));
 		   spr.draw(batch); 
 		  spr.setX(x);
 		   tiempoHerido--;
 		   if (tiempoHerido<=0) herido = false;
 		 }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            float dir = rotationDeg + 90f;

            if (tripleDisparoActivo) {
                for (float offset : new float[]{0f, 45f, -45f}) {
                    float vx = MathUtils.cosDeg(dir + offset) * 5;
                    float vy = MathUtils.sinDeg(dir + offset) * 5;
                    Bullet bala = new Bullet(getNoseX(), getNoseY(), vx, vy, txBala);
                    bala.orientAt(getNoseX(), getNoseY(), dir + offset);
                    juego.agregarBala(bala);
                }
            } else {
                float vx = MathUtils.cosDeg(dir) * 5;
                float vy = MathUtils.sinDeg(dir) * 5;
                Bullet bala = new Bullet(getNoseX(), getNoseY(), vx, vy, txBala);
                bala.orientAt(getNoseX(), getNoseY(), dir);
                juego.agregarBala(bala);
            }

            soundBala.play();
         }

     }
    
    
    
    private float getNoseX() {
        float cx = spr.getX() + spr.getWidth()  / 2f;  // centro de la nave
        float cy = spr.getY() + spr.getHeight() / 2f;
        float halfH = spr.getHeight() / 2f;            // vector punta: (0, halfH) en espacio local

        float rad = rotationDeg * MathUtils.degreesToRadians;
        // Rotamos (0, halfH) por rotationDeg:
        float offX = -halfH * MathUtils.sin(rad);
        float offY =  halfH * MathUtils.cos(rad);

        return cx + offX;
    }
      
    private float getNoseY() {
        float cx = spr.getX() + spr.getWidth()  / 2f;
        float cy = spr.getY() + spr.getHeight() / 2f;
        float halfH = spr.getHeight() / 2f;

        float rad = rotationDeg * MathUtils.degreesToRadians;
        float offX = -halfH * MathUtils.sin(rad);
        float offY =  halfH * MathUtils.cos(rad);

        return cy + offY;
    }
   
   //camvio
    public boolean checkCollision(Ball2 b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            // Si la nave tiene escudo, ignora el daño y rompe el escudo
            if (escudoActivo) {
                desactivarEscudo();
                return false;
            }

            // Rebote normal
            if (xVel == 0) xVel += b.getXSpeed() / 2;
            if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int) xVel / 2);
            xVel = -xVel;
            b.setXSpeed(-b.getXSpeed());

            if (yVel == 0) yVel += b.getySpeed() / 2;
            if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int) yVel / 2);
            yVel = -yVel;
            b.setySpeed(-b.getySpeed());

            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();

            if (vidas <= 0) destruida = true;
            return true;
        }
        return false;
    }

    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
	

    public void aumentarVelocidad() {
    	velocidadBase *= 2;  
    }


    public void cambiarModelo(String rutaNuevaTextura) {
        Texture nuevaTextura = new Texture(Gdx.files.internal(rutaNuevaTextura));
        spr.setTexture(nuevaTextura);
    }

    /*Restaura el modelo original
    public void restaurarModelo() {
        Texture normal = new Texture(Gdx.files.internal("MainShip3.png"));
        spr.setTexture(normal);
        velocidadBase = 2f;                  
        tripleDisparoActivo = false;  
    } */
   
    public Sprite getSprite() {
        return spr;
    }
    
    
    //NUEVO
    public void activarTripleDisparo() {
        tripleDisparoActivo = true;
    }    
    
    public void restaurarTripleDisparto(){
        tripleDisparoActivo = false; 
    	
    }
    
    public void restaurarVelocidad(){
    	velocidadBase = 2f; 
    }
    
    public void activarEscudo() {
        escudoActivo = true;
        tiempoEscudoActivo = DURACION_ESCUDO;
        cambiarModelo("NaveEscudo.png");
    }

    public void actualizarEscudo(float delta) { 
    if (escudoActivo) {
        tiempoEscudoActivo -= delta; // Descontar tiempo real
        if (tiempoEscudoActivo <= 0) {
            desactivarEscudo();
        }
    }
}
    
    public void desactivarEscudo() {
        escudoActivo = false;
        tiempoEscudoActivo = 0f; // Limpiar contador
        cambiarModelo("MainShip3.png");
    }

    public boolean isEscudoActivo() {
        return escudoActivo;
    }

    
}
