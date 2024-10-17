package io.github.killiann;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ball {
    private Body body;
    private float radius;
    private float speed = 5f;

    public Ball(World world, float x, float y, float radius) {
        this.radius = radius;

        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        // Create the body
        body = world.createBody(bodyDef);

        // Create circle shape
        CircleShape circle = new CircleShape();
        circle.setRadius(radius); // Set the radius of the circle

        // Create fixture definition and attach to body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        body.createFixture(fixtureDef);

        circle.dispose(); // Dispose of the shape after creating the fixture
        body.setLinearVelocity(0, -speed * 500);
    }

    public void render(ShapeRenderer shapeRenderer) {
        // Render the ball
        shapeRenderer.setColor(1, 1, 1, 1); // Set color to white
        Vector2 position = body.getPosition();
        shapeRenderer.circle(position.x, position.y, radius); // Draw the circle
    }

    public Body getBody() {
        return body;
    }

    public float getRadius() {
        return this.radius;
    }
}
