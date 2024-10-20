package io.github.killiann;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Paddle {
    private Body body;
    private float width;
    private float height;
    private float speed = 5f;

    public Paddle(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;

        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        // Create the body
        body = world.createBody(bodyDef);

        // Create rectangle shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width / 2, height / 2); // Set size to the paddle

        // Create fixture definition and attach to body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0f;
        body.setUserData(this);
        body.createFixture(fixtureDef);

        rectangle.dispose(); // Dispose of the shape after creating the fixture
    }

    public void render(ShapeRenderer shapeRenderer) {
        // Render the paddle
        shapeRenderer.setColor(40, 40, 40, 1); // Set color to white
        Vector2 position = body.getPosition();
        shapeRenderer.rect(position.x - width / 2, position.y - height / 2, width, height);
    }

    public Body getBody() {
        return body;
    }

    public float getSpeed() {
        return this.speed * 1000;
    }

    public float getHeight() {
        return this.height;
    }

    public float getWidth() {
        return this.width;
    }

}
