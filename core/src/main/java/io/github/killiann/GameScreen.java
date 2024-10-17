package io.github.killiann;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {

    final Main game;
    OrthographicCamera camera;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Paddle paddle;
    private Ball ball;

    private ShapeRenderer shapeRenderer;

    public GameScreen(final Main game) {
        this.game = game;

        // Create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);

        // Initialize Box2D world with no gravity
        world = new World(new Vector2(0, 0), true); // Gravity set to zero
        b2dr = new Box2DDebugRenderer();

        shapeRenderer = new ShapeRenderer();

        // Calculate the center position based on camera dimensions
        float centerX = camera.viewportWidth / 2;
        float centerY = camera.viewportHeight / 2;

        // Create the paddle and ball
        paddle = new Paddle(world, centerX, 30, 120f, 20f); // Set position and size
        ball = new Ball(world, centerX, centerY, 10f);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Check for input and move the body
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            paddle.getBody().setLinearVelocity(-paddle.getSpeed(), paddle.getBody().getLinearVelocity().y); // Move left
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            paddle.getBody().setLinearVelocity(paddle.getSpeed(), paddle.getBody().getLinearVelocity().y); // Move right
        } else {
            paddle.getBody().setLinearVelocity(0, paddle.getBody().getLinearVelocity().y); // Stop horizontal movement
        }

        world.step(delta, 6, 2);

        // Get the ball's position and velocity
        Vector2 ballPosition = ball.getBody().getPosition();
        Vector2 ballVelocity = ball.getBody().getLinearVelocity();

        // Check for boundary collisions and reverse velocity if needed
        if (ballPosition.x - ball.getRadius() < 0) {
            ball.getBody().setLinearVelocity(Math.abs(ballVelocity.x), ballVelocity.y); // Bounce off left edge
        }
        if (ballPosition.x + ball.getRadius() > 480) {
            ball.getBody().setLinearVelocity(-Math.abs(ballVelocity.x), ballVelocity.y); // Bounce off right edge
        }
        if (ballPosition.y + ball.getRadius() > 800) {
            ball.getBody().setLinearVelocity(ballVelocity.x, -Math.abs(ballVelocity.y)); // Bounce off top edge
        }
        if (ballPosition.y - ball.getRadius() < 0) {
            System.out.println("GAME OVER"); // Bounce off bottom edge
        }

        // Check for collision with the paddle
        if (ballPosition.y - ball.getRadius() <= paddle.getBody().getPosition().y + paddle.getHeight() / 2 &&
            ballPosition.x > paddle.getBody().getPosition().x - paddle.getWidth() / 2 &&
            ballPosition.x < paddle.getBody().getPosition().x + paddle.getWidth() / 2) {

            ball.getBody().setLinearVelocity(ballVelocity.x, -ballVelocity.y);

            // Update ball position to avoid sinking into the paddle
            ball.getBody().setTransform(ball.getBody().getPosition().x,
                paddle.getBody().getPosition().y + paddle.getHeight() / 2 + ball.getRadius(),
                ball.getBody().getAngle());
        }

        // Render the paddle and the ball
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        paddle.render(shapeRenderer);
        ball.render(shapeRenderer);
        shapeRenderer.end();

        // Render Box2D debug shapes
        b2dr.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        shapeRenderer.dispose(); // Dispose of ShapeRenderer
    }
}
