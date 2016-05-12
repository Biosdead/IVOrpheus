package IVOrpheus2Final;


import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;

class NoDragDesktopManager extends DefaultDesktopManager
    {
        public void beginDraggingFrame(JComponent f)
        {
            if (!"fixed".equals(f.getClientProperty("dragMode")))
                super.beginDraggingFrame(f);
        }

        public void dragFrame(JComponent f, int newX, int newY)
        {
            if (!"fixed".equals(f.getClientProperty("dragMode")))
                super.dragFrame(f, newX, newY);
        }

        public void endDraggingFrame(JComponent f)
        {
            if (!"fixed".equals(f.getClientProperty("dragMode")))
                super.endDraggingFrame(f);
        }
    }