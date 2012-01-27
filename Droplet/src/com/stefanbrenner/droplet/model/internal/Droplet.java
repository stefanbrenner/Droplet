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

import com.stefanbrenner.droplet.model.ICamera;
import com.stefanbrenner.droplet.model.IDroplet;
import com.stefanbrenner.droplet.model.IFlash;
import com.stefanbrenner.droplet.model.IValve;

public class Droplet extends AbstractModelObject implements IDroplet {

	private static final long serialVersionUID = 1L;

	private CommPortIdentifier port;

	private List<IValve> valves = new ArrayList<IValve>();

	private List<IFlash> flashes = new ArrayList<IFlash>();

	private List<ICamera> cameras = new ArrayList<ICamera>();

	private static final IDroplet instance = new Droplet();

	private Droplet() {

	}

	public static IDroplet getInstance() {
		return instance;
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
