package com.timetopackup.app.tools.color;

/**
 * Interface with callback methods for AmbilWarna dialog.
 */
public interface ColorPickerListener
{
    void onCancel(ColorPickerDialogFragment dialogFragment);
    void onOk(ColorPickerDialogFragment dialogFragment, int color);
}