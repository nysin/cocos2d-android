package org.cocos2d.transitions;

import org.cocos2d.actions.camera.OrbitCamera;
import org.cocos2d.actions.instant.CallFunc;
import org.cocos2d.actions.instant.Hide;
import org.cocos2d.actions.instant.Show;
import org.cocos2d.actions.interval.DelayTime;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.nodes.Scene;

// TODO
/**
 * ZoomFlipY Transition.
 * Flips the screen vertically doing a little zooming out/in
 * The front face is the outgoing scene and the back face is the incoming scene.
 */
public class ZoomFlipYTransition extends OrientedTransitionScene {

    public static ZoomFlipYTransition transition(float t, Scene s, int orientation) {
        return new ZoomFlipYTransition(t, s, orientation);
    }

    public ZoomFlipYTransition(float t, Scene s, int orientation) {
        super(t, s, orientation);
    }

    public void onEnter() {
        super.onEnter();

        IntervalAction inA, outA;
        inScene.setVisible(false);

        float inDeltaZ, inAngleZ;
        float outDeltaZ, outAngleZ;

        if (orientation == Orientation.kOrientationRightOver) {
            inDeltaZ = 90;
            inAngleZ = 270;
            outDeltaZ = 90;
            outAngleZ = 0;
        } else {
            inDeltaZ = -90;
            inAngleZ = 90;
            outDeltaZ = -90;
            outAngleZ = 0;
        }

        inA = Sequence.actions(
                DelayTime.action(duration / 2),
                Show.action(),
                OrbitCamera.action(duration / 2, 1, 0, inAngleZ, inDeltaZ, 90, 0),
                CallFunc.action(this, "finish"));
        outA = Sequence.actions(
                OrbitCamera.action(duration / 2, 1, 0, outAngleZ, outDeltaZ, 90, 0),
                Hide.action(),
                DelayTime.action(duration / 2));

        inScene.runAction(inA);
        outScene.runAction(outA);
    }
}
