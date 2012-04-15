/**************************************************import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.List;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IValve;

 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Droplet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Droplet. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.stefanbrenner.droplet.model.internal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;

import com.stefanbrenner.droplet.model.IDevice;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IValve;
import com.stefanbrenner.droplet.service.impl.DropletDeviceComparator;

/**
 * 
 * @author Stefan Brenner
 */
@XmlRootElement(name = "Droplet")
public class Droplet extends AbstractModelObject implements IDroplet {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "Name")
	private String name;
	
	@XmlElement(name = "Description")
	private String description;
	
	@XmlElement(name = "Device",
			type = AbstractDevice.class)
	@XmlElementWrapper(name = "Devices")
	private List<IDevice> devices = new ArrayList<IDevice>();
	
	@XmlTransient
	private final DropletDeviceComparator comparator = new DropletDeviceComparator();
	
	/**
	 * Initialize this droplet with the default values.
	 * <p>
	 * Creates three valves, one flash and one camera. All devices start with
	 * one empty action.
	 */
	public void initializeWithDefaults() {
		// add 3 valves
		for (int i = 0; i < 3; i++) {
			IValve valve = new Valve();
			valve.setName("Valve " + (i + 1));
			valve.addAction(valve.createNewAction());
			addDevice(valve);
		}
		// add one flash
		Flash flash = new Flash();
		flash.setName("Flash");
		flash.addAction(flash.createNewAction());
		addDevice(flash);
		// add one camera
		Camera camera = new Camera();
		camera.setName("Camera");
		camera.addAction(camera.createNewAction());
		addDevice(camera);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(final String name) {
		firePropertyChange(IDroplet.PROPERTY_NAME, this.name, this.name = name);
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(final String description) {
		firePropertyChange(IDroplet.PROPERTY_DESCRIPTION, this.description, this.description = description);
	}
	
	@Override
	public void removeDevice(final IDevice device) {
		List<IDevice> oldValue = devices;
		devices = new ArrayList<IDevice>(this.devices);
		devices.remove(device);
		firePropertyChange(IDroplet.ASSOCIATION_DEVICES, oldValue, devices);
	}
	
	@Override
	public void addDevice(final IDevice device) {
		List<IDevice> oldValue = devices;
		devices = new ArrayList<IDevice>(this.devices);
		device.setNumber(String.valueOf(devices.size() + 1));
		devices.add(device);
		firePropertyChange(IDroplet.ASSOCIATION_DEVICES, oldValue, devices);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends IDevice> List<T> getDevices(final Class<T> type) {
		List<T> devicesT = new ArrayList<T>();
		
		for (IDevice device : devices) {
			if (type.isAssignableFrom(device.getClass())) {
				devicesT.add((T) device);
			}
		}
		
		return devicesT;
	}
	
	@Override
	public List<IDevice> getDevices() {
		return devices;
	}
	
	@Override
	public void setDevices(final List<IDevice> devices) {
		firePropertyChange(IDroplet.ASSOCIATION_DEVICES, this.devices, this.devices = devices);
	}
	
	@Override
	public void reset() {
		setName(StringUtils.EMPTY);
		setDescription(StringUtils.EMPTY);
		// reset all devices
		for (IDevice device : getDevices(IDevice.class)) {
			device.reset();
		}
	}
	
	@Override
	public DropletDeviceComparator getDeviceComparator() {
		return comparator;
	}
	
}
