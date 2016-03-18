/*
 * Copyright 2016 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mmarquee.automation;

import com.sun.jna.platform.win32.*;
import mmarquee.automation.condition.raw.IUIAutomationCondition;
import mmarquee.automation.controls.AutomationApplication;
import mmarquee.automation.controls.AutomationWindow;
import mmarquee.automation.uiautomation.*;
import mmarquee.automation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HumphreysM on 26/01/2016.
 */
public class UIAutomation {

    private AutomationElement rootElement;
    private IUIAutomation uiAuto;
    private Process process;

    public UIAutomation() {

        uiAuto = ClassFactory.createCUIAutomation();

        rootElement = new AutomationElement(uiAuto.getRootElement());
    }

    /**
     * Launches the application
     * @param command The command to be called
     * @return AutomationApplication that represents the application
     */
    public AutomationApplication launch(String... command) throws java.io.IOException {
        process = Utils.startProcess(command);
        return new AutomationApplication(rootElement, uiAuto, process);
    }

    /**
     * Attaches to the application process
     * @param process Process to attach to
     * @return AutomationApplication that represents the application
     */
    public AutomationApplication attach(Process process) {
        return new AutomationApplication(rootElement, uiAuto, process);
    }

    /**
     * Attaches or launches the application
     * @param command Command to be started
     * @return AutomationApplication that represents the application
     */
    public AutomationApplication launchOrAttach(String... command) throws Exception {
        final Tlhelp32.PROCESSENTRY32.ByReference processEntry =
            new Tlhelp32.PROCESSENTRY32.ByReference();

        boolean found = Utils.findProcessEntry(processEntry, command);

        if (!found) {
            return this.launch(command);
        } else {
            WinNT.HANDLE handle = Utils.getHandleFromProcessEntry(processEntry);

            return new AutomationApplication(rootElement, uiAuto, handle);
        }
    }

    /**
     * Gets the desktop window associated with the title
     * @param title Title to search for
     * @return AutomationWindow The found window
     */
    public AutomationWindow getDesktopWindow(String title) {
        IUIAutomationElement element = null;

        for (int loop = 0; loop <10; loop++) {
            element = this.rootElement.element.findFirst(TreeScope.TreeScope_Descendants,
                    this.uiAuto.createAndCondition(
                            this.uiAuto.createPropertyCondition(PropertyID.Name, title),
                            this.uiAuto.createPropertyCondition(PropertyID.ControlType, ControlType.Window)));

            if (element != null) {
                break;
            }
        }

        return new AutomationWindow(new AutomationElement(element), this.uiAuto);
    }

    /**
     * Gets the list of desktop windows
     * @return List of desktop windows
     */
    public List<AutomationWindow> getDesktopWindows() {
        List<AutomationWindow> result = new ArrayList<AutomationWindow>();
        IUIAutomationCondition condition = uiAuto.createTrueCondition();
        List<AutomationElement> collection = this.rootElement.findAll(TreeScope.TreeScope_Children, condition);

        int length = collection.size();

        for (int count = 0; count < length; count++) {
            AutomationElement element = collection.get(count);

            result.add(new AutomationWindow(element, this.uiAuto));
        }

        return result;
    }

    /**
     * Does this automation object support IUIAutomation2
     * i.e. is it Windows 8 or above?
     * @return supports IUIAutomation2
     */
    public boolean supportsAutomation2 () {
        //return this.uiAuto as IUIAutomation2;
//        return (IUIAutomation2.isAssignableFrom(this.uiAuto.getClass()));
        return false;
    }

    /**
     * Does this automation object support IUIAutomation3
     * i.e. is it Windows 8.1 or above?
     * @return supports IUIAutomation3
     */
    public boolean supportsAutomation3 () {
        //return this.uiAuto as IUIAutomation3;
  //      return (this.uiAuto instanceOf IUIAutomation3);
        return false;
    }
}
