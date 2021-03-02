package hu.bme.mit.train.system;

import com.google.common.collect.Table;
import hu.bme.mit.train.tachograph.Tachograph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	Tachograph tacho;

	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);

		tacho = new Tachograph(controller, user);
	}

	@Test
	public void Tachograph() {
		tacho.record();
		sensor.overrideSpeedLimit(10);
		tacho.record();

		Assert.assertEquals(0, controller.getReferenceSpeed());

		tacho.record();
		user.overrideJoystickPosition(5);
		tacho.record();

		boolean empty = tacho.getTable().isEmpty();
		Assert.assertFalse(empty);
	}

	@Test
	public void LightsTest() {
		Assert.assertFalse(controller.getLights());
		Assert.assertTrue(controller.toggleLights());

		Assert.assertTrue(controller.getLights());
		Assert.assertFalse(controller.toggleLights());
	}

	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void MyMockTest() {
		Assert.assertTrue(true);
	}
	
}
