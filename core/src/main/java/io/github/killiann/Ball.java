package io.github.killiann;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ball {
    private Body body;
    private float radius;
    private float speed;

    public Ball(World world, float x, float y, float radius) {
        this.radius = radius;
        this.speed = 10f;

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
        fixtureDef.friction = 0f;

        body.setUserData(this); // register for collision detection
        body.setLinearVelocity(speed * 100, speed * 100);
        body.createFixture(fixtureDef);

        circle.dispose(); // Dispose of the shape after creating the fixture
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
