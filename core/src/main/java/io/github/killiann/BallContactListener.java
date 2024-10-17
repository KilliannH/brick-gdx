package io.github.killiann;

import com.badlogic.gdx.physics.box2d.*;

public class BallContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Handle the beginning of a contact
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Check if the ball is in contact with a brick
        if ((fixtureA.getBody().getUserData() instanceof Ball && fixtureB.getBody().getUserData() instanceof Brick) ||
            (fixtureA.getBody().getUserData() instanceof Brick && fixtureB.getBody().getUserData() instanceof Ball)) {
            System.out.println("COLLISIONNNNNNN " + fixtureA.getBody().getUserData().toString() + " " + fixtureB.getBody().getUserData().toString());
            // Handle collision response
            // You can reverse the ball's velocity here if needed
            Ball ball = (Ball) (fixtureA.getBody().getUserData() instanceof Ball ? fixtureA.getBody().getUserData() : fixtureB.getBody().getUserData());
            ball.getBody().setLinearVelocity(ball.getBody().getLinearVelocity().x, -ball.getBody().getLinearVelocity().y);

        } else {
            System.out.println("COLLISIONNNNNNN " + fixtureA.getBody().getUserData().toString() + " " + fixtureB.getBody().getUserData().toString());
        }

        // Check for ball and paddle collision
        if ((fixtureA.getBody().getUserData() instanceof Ball && fixtureB.getBody().getUserData() instanceof Paddle) ||
            (fixtureA.getBody().getUserData() instanceof Paddle && fixtureB.getBody().getUserData() instanceof Ball)) {
            System.out.println("COLLISIONNNNNNN " + fixtureA.getBody().getUserData().toString() + " " + fixtureB.getBody().getUserData().toString());

            Ball ball = (Ball) (fixtureA.getBody().getUserData() instanceof Ball ? fixtureA.getBody().getUserData() : fixtureB.getBody().getUserData());
            ball.getBody().setLinearVelocity(ball.getBody().getLinearVelocity().x, -ball.getBody().getLinearVelocity().y);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Handle the end of a contact if needed
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Handle pre-solve if needed
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Handle post-solve if needed
    }
}
