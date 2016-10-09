/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2012 Andreas Maschke

  This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser 
  General Public License as published by the Free Software Foundation; either version 2.1 of the 
  License, or (at your option) any later version.
 
  This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this software; 
  if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jwildfire.create.tina.variation;

import static org.jwildfire.base.mathlib.MathLib.EPSILON;
import static org.jwildfire.base.mathlib.MathLib.fabs;
import static org.jwildfire.base.mathlib.MathLib.sqrt;

import java.util.HashMap;
import java.util.Map;

import org.jwildfire.base.Tools;
import org.jwildfire.create.GradientCreator;
import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;
import org.jwildfire.create.tina.palette.RenderColor;
import org.jwildfire.image.Pixel;
import org.jwildfire.image.SimpleHDRImage;
import org.jwildfire.image.SimpleImage;
import org.jwildfire.image.WFImage;

public abstract class AbstractColorMapWFFunc extends VariationFunc {
  private static final long serialVersionUID = 1L;

  public static final String PARAM_SCALEX = "scale_x";
  public static final String PARAM_SCALEY = "scale_y";
  private static final String PARAM_SCALEZ = "scale_z";
  private static final String PARAM_OFFSETX = "offset_x";
  private static final String PARAM_OFFSETY = "offset_y";
  private static final String PARAM_OFFSETZ = "offset_z";
  private static final String PARAM_TILEX = "tile_x";
  private static final String PARAM_TILEY = "tile_y";
  private static final String PARAM_RESETZ = "reset_z";
  private static final String PARAM_IS_SEQUENCE = "is_sequence";
  private static final String PARAM_SEQUENCE_START = "sequence_start";
  private static final String PARAM_SEQUENCE_DIGITS = "sequence_digits";

  private static final String[] paramNames = { PARAM_SCALEX, PARAM_SCALEY, PARAM_SCALEZ, PARAM_OFFSETX, PARAM_OFFSETY, PARAM_OFFSETZ, PARAM_TILEX, PARAM_TILEY, PARAM_RESETZ, PARAM_IS_SEQUENCE, PARAM_SEQUENCE_START, PARAM_SEQUENCE_DIGITS };

  private double scaleX = 1.0;
  private double scaleY = 1.0;
  private double scaleZ = 0.0;
  private double offsetX = 0.0;
  private double offsetY = 0.0;
  private double offsetZ = 0.0;
  private int tileX = 1;
  private int tileY = 1;
  private int resetZ = 1;
  private int inlinedImageHash = 0;
  private int is_sequence = 0;
  private int sequence_start = 1;
  private int sequence_digits = 4;

  // derived params
  private int imgWidth, imgHeight;
  private Pixel toolPixel = new Pixel();
  private float[] rgbArray = new float[3];

  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount, double pInputX, double pInputY) {
    double x = (pInputX - (offsetX + 0.5) + 1.0) / scaleX * (double) (imgWidth - 1);
    double y = (pInputY - (offsetY + 0.5) + 1.0) / scaleY * (double) (imgHeight - 1);
    int ix = Tools.FTOI(x);
    int iy = Tools.FTOI(y);
    if (this.tileX == 1) {
      if (ix < 0) {
        int nx = ix / imgWidth - 1;
        ix -= nx * imgWidth;
      }
      else if (ix >= imgWidth) {
        int nx = ix / imgWidth;
        ix -= nx * imgWidth;
      }
    }
    if (this.tileY == 1) {
      if (iy < 0) {
        int ny = iy / imgHeight - 1;
        iy -= ny * imgHeight;
      }
      else if (iy >= imgHeight) {
        int ny = iy / imgHeight;
        iy -= ny * imgHeight;
      }
    }

    if (ix >= 0 && ix < imgWidth && iy >= 0 && iy < imgHeight) {
      if (colorMap instanceof SimpleImage) {
        toolPixel.setARGBValue(((SimpleImage) colorMap).getARGBValue(
            ix, iy));
        pVarTP.rgbColor = true;
        pVarTP.redColor = toolPixel.r;
        pVarTP.greenColor = toolPixel.g;
        pVarTP.blueColor = toolPixel.b;
      }
      else {
        ((SimpleHDRImage) colorMap).getRGBValues(rgbArray, ix, iy);
        pVarTP.rgbColor = true;
        pVarTP.redColor = rgbArray[0];
        pVarTP.greenColor = rgbArray[1];
        pVarTP.blueColor = rgbArray[2];
      }
    }
    else {
      pVarTP.rgbColor = true;
      pVarTP.redColor = 0;
      pVarTP.greenColor = 0;
      pVarTP.blueColor = 0;
    }
    double dz = this.offsetZ;
    if (fabs(scaleZ) > EPSILON) {
      double intensity = (0.299 * pVarTP.redColor + 0.588 * pVarTP.greenColor + 0.113 * pVarTP.blueColor) / 255.0;
      dz += scaleZ * intensity;
    }
    if (resetZ != 0) {
      pVarTP.z = dz;
    }
    else {
      pVarTP.z += dz;
    }
    pVarTP.color = getColorIdx(Tools.FTOI(pVarTP.redColor), Tools.FTOI(pVarTP.greenColor), Tools.FTOI(pVarTP.blueColor));
  }

  @Override
  public String[] getParameterNames() {
    return paramNames;
  }

  @Override
  public Object[] getParameterValues() {
    return new Object[] { scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, tileX, tileY, resetZ, is_sequence, sequence_start, sequence_digits };
  }

  @Override
  public void setParameter(String pName, double pValue) {
    if (PARAM_SCALEX.equalsIgnoreCase(pName))
      scaleX = pValue;
    else if (PARAM_SCALEY.equalsIgnoreCase(pName))
      scaleY = pValue;
    else if (PARAM_SCALEZ.equalsIgnoreCase(pName))
      scaleZ = pValue;
    else if (PARAM_OFFSETX.equalsIgnoreCase(pName))
      offsetX = pValue;
    else if (PARAM_OFFSETY.equalsIgnoreCase(pName))
      offsetY = pValue;
    else if (PARAM_OFFSETZ.equalsIgnoreCase(pName))
      offsetZ = pValue;
    else if (PARAM_TILEX.equalsIgnoreCase(pName))
      tileX = Tools.FTOI(pValue);
    else if (PARAM_TILEY.equalsIgnoreCase(pName))
      tileY = Tools.FTOI(pValue);
    else if (PARAM_RESETZ.equalsIgnoreCase(pName))
      resetZ = Tools.FTOI(pValue);
    else if (PARAM_IS_SEQUENCE.equalsIgnoreCase(pName)) {
      is_sequence = Tools.FTOI(pValue);
      clearCurrColorMap();
    }
    else if (PARAM_SEQUENCE_START.equalsIgnoreCase(pName)) {
      sequence_start = Tools.FTOI(pValue);
      clearCurrColorMap();
    }
    else if (PARAM_SEQUENCE_DIGITS.equalsIgnoreCase(pName)) {
      sequence_digits = Tools.FTOI(pValue);
      clearCurrColorMap();
    }
    else
      throw new IllegalArgumentException(pName);
  }

  private WFImage colorMap;
  private RenderColor[] renderColors;
  private Map<RenderColor, Double> colorIdxMap = new HashMap<RenderColor, Double>();

  private double getColorIdx(int pR, int pG, int pB) {
    RenderColor pColor = new RenderColor(pR, pG, pB);
    Double res = colorIdxMap.get(pColor);
    if (res == null) {

      int nearestIdx = 0;
      RenderColor color = renderColors[0];
      double dr, dg, db;
      dr = (color.red - pR);
      dg = (color.green - pG);
      db = (color.blue - pB);
      double nearestDist = sqrt(dr * dr + dg * dg + db * db);
      for (int i = 1; i < renderColors.length; i++) {
        color = renderColors[i];
        dr = (color.red - pR);
        dg = (color.green - pG);
        db = (color.blue - pB);
        double dist = sqrt(dr * dr + dg * dg + db * db);
        if (dist < nearestDist) {
          nearestDist = dist;
          nearestIdx = i;
        }
      }
      res = (double) nearestIdx / (double) (renderColors.length - 1);
      colorIdxMap.put(pColor, res);
    }
    return res;
  }

  @Override
  public void init(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) {
    colorMap = null;
    renderColors = pLayer.getPalette().createRenderPalette(pContext.getFlameRenderer().getFlame().getWhiteLevel());
    final String imageFilename = getResourceValueString(RessourceName.IMAGE_FILENAME);
    final byte[] inlinedImage = getResourceValue(RessourceName.INLINED_IMAGE);
    if (inlinedImage != null) {
      try {
        colorMap = RessourceManager.getImage(inlinedImageHash, inlinedImage);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else if (imageFilename != null && imageFilename.length() > 0) {
      try {
        colorMap = RessourceManager.getImage(getCurrImageFilename(pContext));
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (colorMap == null) {
      colorMap = getDfltImage();
    }
    imgWidth = colorMap.getImageWidth();
    imgHeight = colorMap.getImageHeight();
  }

  private String getCurrImageFilename(FlameTransformationContext pContext) {
    final String imageFilename = getResourceValueString(RessourceName.IMAGE_FILENAME);
    if (is_sequence > 0) {
      int frame = pContext.getFrame() - 1 + sequence_start;
      String baseFilename;
      String fileExt;
      int p = imageFilename.lastIndexOf(".");
      if (p < 0 || p <= sequence_digits || p == imageFilename.length() - 1)
        return imageFilename;
      baseFilename = imageFilename.substring(0, p - sequence_digits);
      fileExt = imageFilename.substring(p, imageFilename.length());

      String number = String.valueOf(frame);
      while (number.length() < sequence_digits) {
        number = "0" + number;
      }
      return baseFilename + number + fileExt;

    }
    else {
      return imageFilename;
    }
  }

  private static SimpleImage dfltImage = null;

  private synchronized SimpleImage getDfltImage() {
    if (dfltImage == null) {
      GradientCreator creator = new GradientCreator();
      dfltImage = creator.createImage(256, 256);
    }
    return dfltImage;
  }

  @Override
  protected void initRessources() {
    addRessource(RessourceName.IMAGE_FILENAME, RessourceType.IMAGE_FILENAME, null);
    addRessource(RessourceName.INLINED_IMAGE, RessourceType.IMAGE_FILE, null);
    addRessource(RessourceName.IMAGE_DESC_SRC, RessourceType.HREF, null);
    addRessource(RessourceName.IMAGE_SRC, RessourceType.HREF, null);
  }

  @Override
  public void setRessource(RessourceName rName, byte[] rValue) {
    setResourceValue(rName, rValue);
    switch (rName) {
    case IMAGE_FILENAME:
    case INLINED_IMAGE:
        clearCurrColorMap();
        break;
    default:
    }
  }

  private void clearCurrColorMap() {
    colorMap = null;
    colorIdxMap.clear();
  }

}
