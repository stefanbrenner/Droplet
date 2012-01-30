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

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

@XmlRootElement(name = "Droplet")
public class Droplet extends AbstractModelObject implements IDroplet {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private CommPortIdentifier port;

	@XmlAttribute(name = "Name")
	private String name;

	@XmlElement(name = "Description")
	private String description;

	@XmlElement(name = "Valve", type = Valve.class)
	@XmlElementWrapper(name = "Valves")
	private List<IValve> valves = new ArrayList<IValve>();

	@XmlElement(name = "Flash", type = Flash.class)
	@XmlElementWrapper(name = "Flashes")
	private List<IFlash> flashes = new ArrayList<IFlash>();

	@XmlElement(name = "Camera", type = Camera.class)
	@XmlElementWrapper(name = "Cameras")
	private List<ICamera> cameras = new ArrayList<ICamera>();

	private static final IDroplet instance = new Droplet();

	private Droplet() {
		// add 3 valves
		for (int i = 0; i < 3; i++) {
			IValve valve = new Valve();
			valve.setName("Valve " + (i + 1));
			valve.addAction(valve.createNewAction());
			valves.add(valve);
		}
		// add one flash
		Flash flash = new Flash();
		flash.setName("580EX");
		flash.addAction(flash.createNewAction());
		flashes.add(flash);
		// add one camera
		Camera camera = new Camera();
		camera.setName("EOS 7D");
		camera.addAction(camera.createNewAction());
		cameras.add(camera);
	}

	public static IDroplet getInstance() {
		return instance;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public CommPortIdentifier getPort() {
		return port;
	}

	@Override
	public void setPort(CommPortIdentifier port) {
		this.port = port;
	}

	@Override
	public List<IValve> getValves() {
		return valves;
	}

	@Override
	public void setValves(List<IValve> valves) {
		this.valves = valves;
	}

	@Override
	public List<IFlash> getFlashes() {
		return flashes;
	}

	@Override
	public void setFlashes(List<IFlash> flashes) {
		this.flashes = flashes;
	}

	@Override
	public List<ICamera> getCameras() {
		return cameras;
	}

	@Override
	public void setCameras(List<ICamera> cameras) {
		this.cameras = cameras;
	}

}
