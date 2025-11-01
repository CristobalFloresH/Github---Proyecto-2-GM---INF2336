package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	
	
	//NUEVO
	private PowerUpSpeed powerUpSpeed;
	private PowerUpTripleDisparo powerUpTripleDisparo;
	private PowerUpEscudo powerUpEscudo;	
	private Texture fondo;
	// CONTROL DE SPAWN DE POWERUPS
	private float tiempoSpawnPowerUp = 0;
	private float siguienteSpawn = 0;
	private final float TIEMPO_MIN_ENTRE_SPAWN = 3f; 
	private final float TIEMPO_MAX_ENTRE_SPAWN = 6f; 
	private Random randomSpawn = new Random();

	

	
	private Nave4 nave;
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		
		fondo = new Texture(Gdx.files.internal("fondo.png"));
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("cancionJuego.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
 
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        nave.setVidas(vidas);
	    siguienteSpawn = randomSpawn.nextFloat() * (TIEMPO_MAX_ENTRE_SPAWN - TIEMPO_MIN_ENTRE_SPAWN) + TIEMPO_MIN_ENTRE_SPAWN;
		generarAsteroidesIniciales();

	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		  tiempoSpawnPowerUp += delta;
		  if (tiempoSpawnPowerUp >= siguienteSpawn) {
			  spawnNuevoPowerUp();
			  tiempoSpawnPowerUp = 0;
			  siguienteSpawn = randomSpawn.nextFloat() * (TIEMPO_MAX_ENTRE_SPAWN - TIEMPO_MIN_ENTRE_SPAWN) + TIEMPO_MIN_ENTRE_SPAWN;
		  	}

          batch.begin();
          batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		  dibujaEncabezado();
	      if (!nave.estaHerido()) {

	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < balls1.size(); j++) {    
		              if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play(0.1f);
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; 
		            }
		      }

		      for (Ball2 ball : balls1) {
		          ball.update();
		      }

		      for (int i=0;i<balls1.size();i++) {
		    	Ball2 ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	      }

	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      nave.actualizarEscudo(delta);


	      for (int i = 0; i < balls1.size(); i++) {
	    	    Ball2 b=balls1.get(i);
	    	    b.draw(batch);

	              if (nave.checkCollision(b)) {
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
              }   	  
  	        }
	      
	      if (nave.estaDestruido()) {
  			if (score > game.getHighScore())
  				game.setHighScore(score);
	    	Screen ss = new PantallaGameOver(game);
  			ss.resize(1200, 800);
  			game.setScreen(ss);
  			dispose();
  		  }
	      
	
	      if (powerUpTripleDisparo != null) {   	    	  
	    	    if (powerUpTripleDisparo.isActivo()) {
	    	    	powerUpTripleDisparo.update();
	    	    	powerUpTripleDisparo.render(batch);

	    	        if (powerUpTripleDisparo.getHitbox().overlaps(nave.getSprite().getBoundingRectangle())) {
	    	        	powerUpTripleDisparo.aplicarEfecto(nave);
	    	        	powerUpTripleDisparo.desactivar();
	    	        }
	    	    }
	    	}
	      
	      if (powerUpSpeed != null) {
	    	    if (powerUpSpeed.isActivo()) {	    	 	    	 
	    	    	powerUpSpeed.update();
	    	    	powerUpSpeed.render(batch);

	    	        if (powerUpSpeed.getHitbox().overlaps(nave.getSprite().getBoundingRectangle())) {
	    	        	powerUpSpeed.aplicarEfecto(nave);
	    	        	powerUpSpeed.desactivar();
	    	        }
	    	    }
	    	}
	      
	      if (powerUpEscudo != null) {
	    	    if (powerUpEscudo.isActivo()) {
	    	        powerUpEscudo.update();
	    	        powerUpEscudo.render(batch);

	    	        if (powerUpEscudo.getHitbox().overlaps(nave.getSprite().getBoundingRectangle())) {
	    	            powerUpEscudo.aplicarEfecto(nave);
	    	            powerUpEscudo.desactivar();
	    	        }
	    	    }
	    	}


	      
	      batch.end();

	      if (balls1.size()==0) {
              
            int nuevoCantAsteroides;


            if (this.ronda == 1) {
               nuevoCantAsteroides = this.cantAsteroides + 3; 
            } else if (this.ronda == 2) {
                nuevoCantAsteroides = this.cantAsteroides + 5;
            } else {
                nuevoCantAsteroides = this.cantAsteroides + 2;
            }

            int nuevaVelX = velXAsteroides + 1;
            int nuevaVelY = velYAsteroides + 1;

			Screen ss = new PantallaJuego(game, this.ronda + 1, nave.getVidas(), score,nuevaVelX, nuevaVelY, nuevoCantAsteroides);
            
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	    	 
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
	
	private void spawnNuevoPowerUp() {
	    if ((powerUpSpeed != null && powerUpSpeed.isActivo()) ||
	        (powerUpTripleDisparo != null && powerUpTripleDisparo.isActivo()) ||
	        (powerUpEscudo != null && powerUpEscudo.isActivo())) {
	        return;
	    }

	    int tipo = randomSpawn.nextInt(3); 
	    float posX = randomSpawn.nextInt((int) (Gdx.graphics.getWidth() - 64));
	    switch (tipo) {
	        case 0:
	            powerUpSpeed = new PowerUpSpeed();
	            powerUpSpeed.posicion.x = posX;
	            break;
	        case 1:
	            powerUpTripleDisparo = new PowerUpTripleDisparo();
	            powerUpTripleDisparo.posicion.x = posX;
	            break;
	        case 2:
	            powerUpEscudo = new PowerUpEscudo();
	            powerUpEscudo.posicion.x = posX;
	            break;
	    }
	
}
	private void generarAsteroidesIniciales() {
	    Random r = new Random();


	    final float P_DEFAULT   = 0.70f;
	    final float P_ERRATIC   = 0.15f;
	    final float P_OSCILATE  = 0.10f;
	    // P_CIRCULAR = 1 - (anteriores) = 0.05

	    for (int i = 0; i < cantAsteroides; i++) {
	        int startX = r.nextInt((int) Gdx.graphics.getWidth());
	        int startY = 50 + r.nextInt((int) Gdx.graphics.getHeight() - 50);
	        int size   = 20 + r.nextInt(10);
	        int vx     = velXAsteroides + r.nextInt(2);
	        int vy     = velYAsteroides + r.nextInt(2);

	        float roll = r.nextFloat();
	        int pick;
	        if (roll < P_DEFAULT) {
	            pick = 3;           // default (rebote)
	        } else if (roll < P_DEFAULT + P_ERRATIC) {
	            pick = 0;           // errático
	        } else if (roll < P_DEFAULT + P_ERRATIC + P_OSCILATE) {
	            pick = 1;           // oscilante
	        } else {
	            pick = 2;           // circular
	        }

	        MovementStrategy strategy;
	        Texture textura;
	        float ancho = 40, alto = 40; 

	        switch (pick) {
	            case 0: // Errático
	                strategy = new MovimientoErratico(1, 4);
	                textura  = new Texture(Gdx.files.internal("meteoro_erratico.png"));
	                ancho = 60; alto = 60;
	                break;

	            case 1: // Oscilante
	                strategy = new MovimientoVelocidadCambiante(1.0f, 0.7f, 1.2f, 12);
	                textura  = new Texture(Gdx.files.internal("meteoro_oscila.png"));
	                ancho = 60; alto = 60;
	                break;

	            case 2: // Circular
	                float radius   = 40 + r.nextInt(60);
	                float angSpeed = r.nextBoolean() ? 2.5f : -2.5f;
	                strategy = new MovimientoCircular(startX, startY, radius, angSpeed);
	                textura  = new Texture(Gdx.files.internal("meteoro_circular.png"));
	                ancho = 60; alto = 60;
	                vx = 0; vy = 0;
	                break;

	            default: // DEFAULT (rebote clásico): 3 tamaños distintos
	                strategy = new MovimientoDefault();
	                String[] opciones = { "aGreySmall.png", "aGreyMedium4.png", "aGreyLarge.png" };
	                String archivo = opciones[r.nextInt(opciones.length)];
	                textura = new Texture(Gdx.files.internal(archivo));


	                if (archivo.contains("Small"))  { ancho = 60; alto = 60; }
	                if (archivo.contains("Medium")) { ancho = 80; alto = 80; }
	                if (archivo.contains("Large"))  { ancho = 120; alto = 120; }
	                break;
	        }


	        Ball2 bb = new Ball2(startX, startY, size, vx, vy, textura, strategy);

	        bb.getSprite().setSize(ancho, alto);

	        balls1.add(bb);
	        balls2.add(bb);
	    }
	}
}
