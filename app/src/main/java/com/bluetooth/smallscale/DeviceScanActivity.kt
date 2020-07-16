import android.app.ListActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*


private const val SCAN_PERIOD: Long = 10000

/**
 * Activity for scanning and displaying available BLE devices.
 */
class DeviceScanActivity(
    private val bluetoothAdapter: BluetoothAdapter,
    private val handler: Handler
) : ListActivity() {

    private var mScanning: Boolean = false
    private var serviceUuid: Array<UUID> = arrayOf(UUID.fromString("B82AAAFC-13B1-4F7B-8DF0-A3094771B8F9"))

    private fun scanLeDevice(enable: Boolean) {
        when (enable) {
            true -> {
                // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    mScanning = false
                    bluetoothAdapter.stopLeScan(leScanCallback)
                }, SCAN_PERIOD)
                mScanning = true
                bluetoothAdapter.startLeScan(serviceUuid, leScanCallback)
            }
            else -> {
                mScanning = false
                bluetoothAdapter.stopLeScan(leScanCallback)
            }
        }
    }

    class LeDeviceListAdapter : BaseAdapter() {
        private val mLeDevices: ArrayList<BluetoothDevice> = ArrayList()
        //private val mInflator: LayoutInflater
        fun addDevice(device: BluetoothDevice) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device)
            }
        }

        fun getDevice(position: Int): BluetoothDevice {
            return mLeDevices[position]
        }

        fun clear() {
            mLeDevices.clear()
        }

        override fun getCount(): Int {
            return mLeDevices.size
        }

        override fun getItem(i: Int): Any {
            return mLeDevices[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View? {
//            var view: View? = view
//            val viewHolder: ViewHolder
//            // General ListView optimization code.
//            if (view == null) {
//                view = mInflator.inflate(R.layout.listitem_device, null)
//                viewHolder = ViewHolder()
//                viewHolder.deviceAddress = view.findViewById(R.id.device_address) as TextView
//                viewHolder.deviceName = view.findViewById(R.id.device_name) as TextView
//                view.setTag(viewHolder)
//            } else {
//                viewHolder = view.getTag()
//            }
//            val device = mLeDevices[i]
//            val deviceName = device.name
//            if (deviceName != null && deviceName.length > 0) viewHolder.deviceName.setText(
//                deviceName
//            ) else viewHolder.deviceName.setText(R.string.unknown_device)
//            viewHolder.deviceAddress.setText(device.address)
//            return view
            return null
        }

        init {
            //mInflator = this.DeviceScanActivity.getLayoutInflater()
        }
    }

    val leDeviceListAdapter: LeDeviceListAdapter = LeDeviceListAdapter()

    private val leScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        runOnUiThread {
            leDeviceListAdapter.addDevice(device)
            leDeviceListAdapter.notifyDataSetChanged()

            //var bluetoothGatt: BluetoothGatt? = device.connectGatt(this, false, gattCallback)
        }
    }
}