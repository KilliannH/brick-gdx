package io.github.killiann;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Brick {
    private Body body;
    private float width;
    private float height;

    public Brick(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;

        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // Bricks should be static
        bodyDef.position.set(x, y);

        // Create the body
        body = world.createBody(bodyDef);

        // Create rectangle shape
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width / 2, height / 2); // Set size

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
        shapeRenderer.setColor(40, 40, 40, 1); // Set color to red
        Vector2 position = body.getPosition();
        shapeRenderer.rect(position.x - width / 2, position.y - height / 2, width, height); // Draw the rectangle
    }

    public Body getBody() {
        return body;
    }
}
