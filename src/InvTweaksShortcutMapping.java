import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;


/**
 * 
 * @author Jimeo Wan
 *
 */
public class InvTweaksShortcutMapping {

    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger("InvTweaks");
    
    private List<Integer> keysToHold = new LinkedList<Integer>();

    public InvTweaksShortcutMapping(int keyCode) {
    	keysToHold.add(keyCode);
    }

    public InvTweaksShortcutMapping(int[] keyCodes) {
    	for (int keyCode : keyCodes) {
    		keysToHold.add(keyCode);
    	}
    }
    public InvTweaksShortcutMapping(String keyName) {
    	this(new String[]{ keyName });
    }
    
    public InvTweaksShortcutMapping(String[] keyNames) {
    	for (String keyName : keyNames) {
            // - Accept both KEY_### and ###, in case someone
            //   takes the LWJGL Javadoc at face value
            // - Accept LALT & RALT instead of LMENU & RMENU
    		keyName = keyName.trim().replace("KEY_", "").replace("ALT", "MENU");
    		keysToHold.add(Keyboard.getKeyIndex(keyName));
    	}
    }
    
    public boolean isTriggered(Map<Integer, Boolean> holdKeys) {
    	for (Integer keyToHold : keysToHold) {
    		if (keyToHold != Keyboard.KEY_LCONTROL) {
    			if (!holdKeys.get(keyToHold)) {
    				return false;
    			}
    		}
			// AltGr also activates LCtrl, make sure the real LCtrl has been pressed
    		else if (!holdKeys.get(keyToHold) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
    			return false;
    		}
    	}
		return true;
		/*

        if (shortcutKeysStatus.get(keyCode) && 
                (keyCode != 29 || !Keyboard.isKeyDown(184))) {
            return keyCode;
        }*/
    }
    
    public List<Integer> getKeyCodes() {
    	return this.keysToHold;
    }
}
