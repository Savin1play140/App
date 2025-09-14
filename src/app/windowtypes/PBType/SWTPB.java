package app.windowtypes.PBType;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

public class SWTPB extends ProgressBarType {
  private Shell s;
  
  public boolean shutdown() {
    if (!this.s.isDisposed())
      this.s.dispose(); 
    return this.s.isDisposed();
  }
  
  public boolean run() {
    this.s = new Shell(new Display());
    this.s.setText("Loading...");
    this.s.setLayout((Layout)new GridLayout());
    this.s.setSize(200, 65);
    this.s.open();
    return !this.s.isDisposed();
  }
}
