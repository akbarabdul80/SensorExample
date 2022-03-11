package com.zero.sensorexample

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zero.sensorexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SensorEventListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mSensorManager: SensorManager by lazy {
        getSystemService(SENSOR_SERVICE) as SensorManager
    }

    private val mlightSensor: Sensor by lazy {
        mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }
    private val mProximitySensor: Sensor by lazy {
        mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in sensorList) {
            binding.tvListSensor.append("${sensor.name} \n")
        }

    }

    override fun onStart() {
        super.onStart()
        mSensorManager.registerListener(this, mlightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when (p0?.sensor?.type) {
            Sensor.TYPE_LIGHT -> {
                binding.tvLight.text = resources.getString(R.string.title_light, p0.values[0])
            }
            Sensor.TYPE_PROXIMITY -> {
                binding.tvProximity.text = baseContext.getString(R.string.title_proximity, p0.values[0])
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}