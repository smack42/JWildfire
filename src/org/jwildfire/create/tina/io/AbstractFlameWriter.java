/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2014 Andreas Maschke

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
package org.jwildfire.create.tina.io;

import static org.jwildfire.base.mathlib.MathLib.EPSILON;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_BACKGROUND_IMAGE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_FADE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_PARAM1;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_PARAM2;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_PARAM3;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_PARAM4;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_PARAM5;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_PARAM6;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_ROTATE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_SCALE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_DOF_SHAPE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_POS_X;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_POS_Y;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_CAM_POS_Z;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP_HOFFSET;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP_HSCALE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP_LCOLOR_ADD;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP_LCOLOR_SCALE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP_VOFFSET;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_GRADIENT_MAP_VSCALE;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_LAYER_NAME;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_SATURATION;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_SMOOTH_GRADIENT;
import static org.jwildfire.create.tina.io.AbstractFlameReader.ATTR_WHITE_LEVEL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.animate.AnimationService;
import org.jwildfire.create.tina.animate.AnimationService.MotionCurveAttribute;
import org.jwildfire.create.tina.base.DrawMode;
import org.jwildfire.create.tina.base.Flame;
import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.Stereo3dMode;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.motion.MotionCurve;
import org.jwildfire.create.tina.base.solidrender.LightDiffFuncPreset;
import org.jwildfire.create.tina.base.solidrender.MaterialSettings;
import org.jwildfire.create.tina.base.solidrender.PointLight;
import org.jwildfire.create.tina.palette.RGBPalette;
import org.jwildfire.create.tina.render.dof.DOFBlurShape;
import org.jwildfire.create.tina.variation.RessourceName;
import org.jwildfire.create.tina.variation.Variation;
import org.jwildfire.create.tina.variation.VariationFunc;

public class AbstractFlameWriter {

  protected List<SimpleXMLBuilder.Attribute<?>> createXFormAttrList(SimpleXMLBuilder pXB, Layer pLayer, XForm pXForm) throws Exception {
    List<SimpleXMLBuilder.Attribute<?>> attrList = new ArrayList<SimpleXMLBuilder.Attribute<?>>();
    attrList.add(pXB.createAttr("weight", pXForm.getWeight()));
    attrList.add(pXB.createAttr("color", pXForm.getColor()));
    attrList.add(pXB.createAttr("symmetry", pXForm.getColorSymmetry()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MATERIAL, pXForm.getMaterial()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MATERIAL_SPEED, pXForm.getMaterialSpeed()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_GAMMA, pXForm.getModGamma()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_GAMMA_SPEED, pXForm.getModGammaSpeed()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_CONTRAST, pXForm.getModContrast()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_CONTRAST_SPEED, pXForm.getModContrastSpeed()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_SATURATION, pXForm.getModSaturation()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_SATURATION_SPEED, pXForm.getModSaturationSpeed()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_HUE, pXForm.getModHue()));
    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_MOD_HUE_SPEED, pXForm.getModHueSpeed()));
    if (pXForm.getDrawMode().equals(DrawMode.OPAQUE)) {
      attrList.add(pXB.createAttr("opacity", pXForm.getOpacity()));
    }
    else if (pXForm.getDrawMode().equals(DrawMode.HIDDEN)) {
      attrList.add(pXB.createAttr("opacity", 0.0));
    }

    UniqueNamesMaker namesMaker = new UniqueNamesMaker();
    for (int vIdx = 0; vIdx < pXForm.getVariationCount(); vIdx++) {
      Variation v = pXForm.getVariation(vIdx);
      VariationFunc func = v.getFunc();

      String fName = namesMaker.makeUnique(func.getName());

      attrList.add(pXB.createAttr(fName, v.getAmount()));
      attrList.add(pXB.createAttr(fName + "_" + AbstractFlameReader.ATTR_FX_PRIORITY, v.getPriority()));
      // params
      {
        String params[] = func.getParameterNames();
        if (params != null) {
          Object vals[] = func.getParameterValues();
          for (int i = 0; i < params.length; i++) {
            if (vals[i] instanceof Integer) {
              attrList.add(pXB.createAttr((fName + "_" + params[i]), (Integer) vals[i]));
            }
            else if (vals[i] instanceof Double) {
              attrList.add(pXB.createAttr((fName + "_" + params[i]), (Double) vals[i]));
            }
            else {
              throw new IllegalStateException();
            }
            MotionCurve curve = v.getMotionCurve(params[i]);
            if (curve != null) {
              writeMotionCurve(pXB, attrList, fName + "_" + params[i], curve);
            }
          }
        }
      }
      // curves
      List<String> blackList = Collections.emptyList();
      writeMotionCurves(v, pXB, attrList, fName + "_", blackList);
      // ressources
      {
        final RessourceName[] ressNames = func.getRessourceNames();
        if (ressNames != null) for (RessourceName rName : ressNames) {
            final byte[] val = func.getRessource(rName);
            String hexStr = val != null && val.length > 0 ? Tools.byteArrayToHexString(val) : "";
            attrList.add(pXB.createAttr((fName + "_" + rName.toString()), hexStr));
        }
      }
    }

    attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_XY_COEFS, pXForm.getXYCoeff00() + " " + pXForm.getXYCoeff01() + " " + pXForm.getXYCoeff10() + " " + pXForm.getXYCoeff11() + " " + pXForm.getXYCoeff20() + " " + pXForm.getXYCoeff21()));
    if (pXForm.isHasXYPostCoeffs()) {
      attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_XY_POST, pXForm.getXYPostCoeff00() + " " + pXForm.getXYPostCoeff01() + " " + pXForm.getXYPostCoeff10() + " " + pXForm.getXYPostCoeff11() + " " + pXForm.getXYPostCoeff20() + " " + pXForm.getXYPostCoeff21()));
    }
    if (pXForm.isHasYZCoeffs()) {
      attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_YZ_COEFS, pXForm.getYZCoeff00() + " " + pXForm.getYZCoeff01() + " " + pXForm.getYZCoeff10() + " " + pXForm.getYZCoeff11() + " " + pXForm.getYZCoeff20() + " " + pXForm.getYZCoeff21()));
    }
    if (pXForm.isHasYZPostCoeffs()) {
      attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_YZ_POST, pXForm.getYZPostCoeff00() + " " + pXForm.getYZPostCoeff01() + " " + pXForm.getYZPostCoeff10() + " " + pXForm.getYZPostCoeff11() + " " + pXForm.getYZPostCoeff20() + " " + pXForm.getYZPostCoeff21()));
    }
    if (pXForm.isHasZXCoeffs()) {
      attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_ZX_COEFS, pXForm.getZXCoeff00() + " " + pXForm.getZXCoeff01() + " " + pXForm.getZXCoeff10() + " " + pXForm.getZXCoeff11() + " " + pXForm.getZXCoeff20() + " " + pXForm.getZXCoeff21()));
    }
    if (pXForm.isHasZXPostCoeffs()) {
      attrList.add(pXB.createAttr(AbstractFlameReader.ATTR_ZX_POST, pXForm.getZXPostCoeff00() + " " + pXForm.getZXPostCoeff01() + " " + pXForm.getZXPostCoeff10() + " " + pXForm.getZXPostCoeff11() + " " + pXForm.getZXPostCoeff20() + " " + pXForm.getZXPostCoeff21()));
    }
    {
      String hs = "";
      for (int i = 0; i < pLayer.getXForms().size() - 1; i++) {
        hs += pXForm.getModifiedWeights()[i] + " ";
      }
      hs += pXForm.getModifiedWeights()[pLayer.getXForms().size() - 1];
      attrList.add(pXB.createAttr("chaos", hs));
    }

    String xName = pXForm.getName().replaceAll("\"", "");
    if (!xName.equals("")) {
      attrList.add(pXB.createAttr("name", xName));
    }

    List<String> blackList = Collections.emptyList();
    writeMotionCurves(pXForm, pXB, attrList, null, blackList);
    return attrList;
  }

  protected class UniqueNamesMaker {
    private final Map<String, String> namesMap = new HashMap<String, String>();

    public String makeUnique(String pName) {
      int nameCounter = 1;
      String name = pName;
      while (namesMap.get(name) != null) {
        name = pName + "#" + (nameCounter++) + "#";
      }
      namesMap.put(name, name);
      return name;
    }
  }

  protected List<SimpleXMLBuilder.Attribute<?>> createFlameAttributes(Flame pFlame, SimpleXMLBuilder xb) throws Exception {
    List<SimpleXMLBuilder.Attribute<?>> attrList = new ArrayList<SimpleXMLBuilder.Attribute<?>>();
    String fName = pFlame.getName().replaceAll("\"", "");
    if (!fName.equals("")) {
      attrList.add(xb.createAttr("name", fName));
    }

    String bgImagefilename = pFlame.getBGImageFilename().replaceAll("\"", "");
    if (!bgImagefilename.equals("")) {
      attrList.add(xb.createAttr(ATTR_BACKGROUND_IMAGE, bgImagefilename));
    }

    if (pFlame.getLayers().size() == 1) {
      String name = pFlame.getFirstLayer().getName().replaceAll("\"", "");
      if (!name.equals("")) {
        attrList.add(xb.createAttr(ATTR_LAYER_NAME, name));
      }
      String gradientMapFilename = pFlame.getFirstLayer().getGradientMapFilename().replaceAll("\"", "");
      if (!gradientMapFilename.equals("")) {
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP, gradientMapFilename));
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP_HOFFSET, pFlame.getFirstLayer().getGradientMapHorizOffset()));
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP_HSCALE, pFlame.getFirstLayer().getGradientMapHorizScale()));
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP_VOFFSET, pFlame.getFirstLayer().getGradientMapVertOffset()));
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP_VSCALE, pFlame.getFirstLayer().getGradientMapVertScale()));
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP_LCOLOR_ADD, pFlame.getFirstLayer().getGradientMapLocalColorAdd()));
        attrList.add(xb.createAttr(ATTR_GRADIENT_MAP_LCOLOR_SCALE, pFlame.getFirstLayer().getGradientMapLocalColorScale()));
      }
      attrList.add(xb.createAttr(ATTR_SMOOTH_GRADIENT, pFlame.getFirstLayer().isSmoothGradient() ? "1" : "0"));
    }

    attrList.add(xb.createAttr("version", Tools.APP_TITLE + " " + Tools.APP_VERSION));
    attrList.add(xb.createAttr("size", pFlame.getWidth() + " " + pFlame.getHeight()));
    attrList.add(xb.createAttr("center", pFlame.getCentreX() + " " + pFlame.getCentreY()));
    attrList.add(xb.createAttr("scale", pFlame.getPixelsPerUnit()));
    attrList.add(xb.createAttr("rotate", pFlame.getCamRoll()));
    attrList.add(xb.createAttr("filter", pFlame.getSpatialFilterRadius()));
    attrList.add(xb.createAttr("filter_kernel", pFlame.getSpatialFilterKernel().toString()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SPATIAL_OVERSAMPLE, pFlame.getSpatialOversampling()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_COLOR_OVERSAMPLE, pFlame.getColorOversampling()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SAMPLE_JITTERING, pFlame.isSampleJittering() ? 1 : 0));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_NOISE_FILTER, pFlame.isPostNoiseFilter() ? 1 : 0));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_NOISE_FILTER_THRESHOLD, pFlame.getPostNoiseFilterThreshold()));
    attrList.add(xb.createAttr("quality", pFlame.getSampleDensity()));
    attrList.add(xb.createAttr("background", (double) pFlame.getBGColorRed() / 255.0 + " " + (double) pFlame.getBGColorGreen() / 255.0 + " " + (double) pFlame.getBGColorBlue() / 255.0));
    attrList.add(xb.createAttr("bg_transparency", pFlame.isBGTransparency() ? "1" : "0"));
    attrList.add(xb.createAttr("brightness", pFlame.getBrightness()));
    attrList.add(xb.createAttr(ATTR_SATURATION, pFlame.getSaturation()));
    attrList.add(xb.createAttr("gamma", pFlame.getGamma()));
    attrList.add(xb.createAttr("gamma_threshold", pFlame.getGammaThreshold()));
    attrList.add(xb.createAttr("vibrancy", pFlame.getVibrancy()));
    attrList.add(xb.createAttr("contrast", pFlame.getContrast()));
    attrList.add(xb.createAttr(ATTR_WHITE_LEVEL, pFlame.getWhiteLevel()));
    attrList.add(xb.createAttr("temporal_samples", 1.0));
    attrList.add(xb.createAttr("cam_zoom", pFlame.getCamZoom()));
    attrList.add(xb.createAttr("cam_pitch", (pFlame.getCamPitch() * Math.PI) / 180.0));
    attrList.add(xb.createAttr("cam_yaw", (pFlame.getCamYaw() * Math.PI) / 180.0));
    attrList.add(xb.createAttr("cam_persp", pFlame.getCamPerspective()));
    attrList.add(xb.createAttr("cam_xfocus", pFlame.getFocusX()));
    attrList.add(xb.createAttr("cam_yfocus", pFlame.getFocusY()));
    attrList.add(xb.createAttr("cam_zfocus", pFlame.getFocusZ()));
    if (pFlame.getDimishZ() != 0.0) {
      attrList.add(xb.createAttr("cam_zdimish", pFlame.getDimishZ()));
    }
    attrList.add(xb.createAttr(ATTR_CAM_POS_X, pFlame.getCamPosX()));
    attrList.add(xb.createAttr(ATTR_CAM_POS_Y, pFlame.getCamPosY()));
    attrList.add(xb.createAttr(ATTR_CAM_POS_Z, pFlame.getCamPosZ()));
    attrList.add(xb.createAttr("cam_zpos", pFlame.getCamZ()));
    attrList.add(xb.createAttr("cam_dof", pFlame.getCamDOF()));
    attrList.add(xb.createAttr("cam_dof_area", pFlame.getCamDOFArea()));
    attrList.add(xb.createAttr("cam_dof_exponent", pFlame.getCamDOFExponent()));
    if (pFlame.isNewCamDOF()) {
      attrList.add(xb.createAttr("new_dof", "1"));
    }

    attrList.add(xb.createAttr(ATTR_CAM_DOF_SHAPE, pFlame.getCamDOFShape().toString()));
    attrList.add(xb.createAttr(ATTR_CAM_DOF_SCALE, pFlame.getCamDOFScale()));
    attrList.add(xb.createAttr(ATTR_CAM_DOF_ROTATE, pFlame.getCamDOFAngle()));
    attrList.add(xb.createAttr(ATTR_CAM_DOF_FADE, pFlame.getCamDOFFade()));
    DOFBlurShape shape = pFlame.getCamDOFShape().getDOFBlurShape();
    if (shape != null) {
      List<String> paramNames = shape.getParamNames();
      if (paramNames.size() > 0) {
        attrList.add(xb.createAttr(ATTR_CAM_DOF_PARAM1, pFlame.getCamDOFParam1()));
        if (paramNames.size() > 1) {
          attrList.add(xb.createAttr(ATTR_CAM_DOF_PARAM2, pFlame.getCamDOFParam2()));
          if (paramNames.size() > 2) {
            attrList.add(xb.createAttr(ATTR_CAM_DOF_PARAM3, pFlame.getCamDOFParam3()));
            if (paramNames.size() > 3) {
              attrList.add(xb.createAttr(ATTR_CAM_DOF_PARAM4, pFlame.getCamDOFParam4()));
              if (paramNames.size() > 4) {
                attrList.add(xb.createAttr(ATTR_CAM_DOF_PARAM5, pFlame.getCamDOFParam5()));
                if (paramNames.size() > 5) {
                  attrList.add(xb.createAttr(ATTR_CAM_DOF_PARAM6, pFlame.getCamDOFParam6()));
                }
              }
            }
          }
        }
      }
    }

    if (pFlame.isPreserveZ()) {
      attrList.add(xb.createAttr("preserve_z", "1"));
    }
    if (pFlame.getResolutionProfile() != null && pFlame.getResolutionProfile().length() > 0)
      attrList.add(xb.createAttr("resolution_profile", pFlame.getResolutionProfile()));
    if (pFlame.getQualityProfile() != null && pFlame.getQualityProfile().length() > 0)
      attrList.add(xb.createAttr("quality_profile", pFlame.getQualityProfile()));
    if (pFlame.getAntialiasAmount() > EPSILON) {
      attrList.add(xb.createAttr("antialias_amount", pFlame.getAntialiasAmount()));
      attrList.add(xb.createAttr("antialias_radius", pFlame.getAntialiasRadius()));
    }
    if (pFlame.getMotionBlurLength() > 0) {
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_MOTIONBLUR_LENGTH, pFlame.getMotionBlurLength()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_MOTIONBLUR_TIMESTEP, pFlame.getMotionBlurTimeStep()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_MOTIONBLUR_DECAY, pFlame.getMotionBlurDecay()));
    }
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_SYMMETRY_TYPE, pFlame.getPostSymmetryType().toString()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_SYMMETRY_ORDER, pFlame.getPostSymmetryOrder()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_SYMMETRY_CENTREX, pFlame.getPostSymmetryCentreX()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_SYMMETRY_CENTREY, pFlame.getPostSymmetryCentreY()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_SYMMETRY_DISTANCE, pFlame.getPostSymmetryDistance()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POST_SYMMETRY_ROTATION, pFlame.getPostSymmetryRotation()));

    if (pFlame.getStereo3dMode() != Stereo3dMode.NONE) {
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_MODE, pFlame.getStereo3dMode().toString()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_ANGLE, pFlame.getStereo3dAngle()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_EYE_DIST, pFlame.getStereo3dEyeDist()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_FOCAL_OFFSET, pFlame.getStereo3dFocalOffset()));
      if (pFlame.getStereo3dMode() == Stereo3dMode.ANAGLYPH) {
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_LEFT_EYE_COLOR, pFlame.getStereo3dLeftEyeColor().toString()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_RIGHT_EYE_COLOR, pFlame.getStereo3dRightEyeColor().toString()));
      }
      else if (pFlame.getStereo3dMode() == Stereo3dMode.INTERPOLATED_IMAGES) {
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_INTERPOLATED_IMAGE_COUNT, pFlame.getStereo3dInterpolatedImageCount()));
      }
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_PREVIEW, pFlame.getStereo3dPreview().toString()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_STEREO3D_SWAP_SIDES, pFlame.isStereo3dSwapSides() ? "1" : "0"));
    }

    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_FRAME, pFlame.getFrame()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_FRAME_COUNT, pFlame.getFrameCount()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_FPS, pFlame.getFps()));

    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POSTBLUR_RADIUS, pFlame.getPostBlurRadius()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POSTBLUR_FADE, pFlame.getPostBlurFade()));
    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_POSTBLUR_FALLOFF, pFlame.getPostBlurFallOff()));

    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_ZBUFFER_SCALE, pFlame.getZBufferScale()));

    if (pFlame.getSolidRenderSettings().isSolidRenderingEnabled()) {
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_ENABLED, pFlame.getSolidRenderSettings().isSolidRenderingEnabled()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_ENABLED, pFlame.getSolidRenderSettings().isAoEnabled()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_INTENSITY, pFlame.getSolidRenderSettings().getAoIntensity()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_SEARCH_RADIUS, pFlame.getSolidRenderSettings().getAoSearchRadius()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_BLUR_RADIUS, pFlame.getSolidRenderSettings().getAoBlurRadius()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_RADIUS_SAMPLES, pFlame.getSolidRenderSettings().getAoRadiusSamples()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_AZIMUTH_SAMPLES, pFlame.getSolidRenderSettings().getAoAzimuthSamples()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_FALLOFF, pFlame.getSolidRenderSettings().getAoFalloff()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_AO_AFFECT_DIFFUSE, pFlame.getSolidRenderSettings().getAoAffectDiffuse()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_SHADOW_TYPE, pFlame.getSolidRenderSettings().getShadowType().toString()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_SHADOW_SMOOTH_RADIUS, pFlame.getSolidRenderSettings().getShadowSmoothRadius()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_SHADOWMAP_SIZE, pFlame.getSolidRenderSettings().getShadowmapSize()));
      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_SHADOWMAP_BIAS, pFlame.getSolidRenderSettings().getShadowmapBias()));

      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_COUNT, pFlame.getSolidRenderSettings().getMaterials().size()));
      for (int i = 0; i < pFlame.getSolidRenderSettings().getMaterials().size(); i++) {
        MaterialSettings material = pFlame.getSolidRenderSettings().getMaterials().get(i);
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_DIFFUSE + i, material.getDiffuse()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_AMBIENT + i, material.getAmbient()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_PHONG + i, material.getPhong()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_PHONG_SIZE + i, material.getPhongSize()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_PHONG_RED + i, material.getPhongRed()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_PHONG_GREEN + i, material.getPhongGreen()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_PHONG_BLUE + i, material.getPhongBlue()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_REFL_MAP_INTENSITY + i, material.getReflMapIntensity()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_REFL_MAP_FILENAME + i, material.getReflMapFilename() != null ? material.getReflMapFilename() : ""));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_REFL_MAPPING + i, material.getReflectionMapping().toString()));
        if (material.getLightDiffFunc() instanceof LightDiffFuncPreset)
          attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_MATERIAL_LIGHT_DIFF_FUNC + i, ((LightDiffFuncPreset) material.getLightDiffFunc()).toString()));
      }

      attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_COUNT, pFlame.getSolidRenderSettings().getLights().size()));
      for (int i = 0; i < pFlame.getSolidRenderSettings().getLights().size(); i++) {
        PointLight light = pFlame.getSolidRenderSettings().getLights().get(i);
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_X + i, light.getX()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_Y + i, light.getY()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_Z + i, light.getZ()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_INTENSITY + i, light.getIntensity()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_RED + i, light.getRed()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_GREEN + i, light.getGreen()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_BLUE + i, light.getBlue()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_SHADOWS + i, light.isCastShadows()));
        attrList.add(xb.createAttr(AbstractFlameReader.ATTR_SLD_RENDER_LIGHT_SHADOW_INTENSITY + i, light.getShadowIntensity()));
      }
    }

    writeMotionCurves(pFlame, xb, attrList, null, flameAttrMotionCurveBlackList);

    attrList.add(xb.createAttr(AbstractFlameReader.ATTR_CHANNEL_MIXER_MODE, pFlame.getChannelMixerMode().toString()));
    switch (pFlame.getChannelMixerMode()) {
      case BRIGHTNESS:
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_RR_CURVE, pFlame.getMixerRRCurve());
        break;
      case RGB:
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_RR_CURVE, pFlame.getMixerRRCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_GG_CURVE, pFlame.getMixerGGCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_BB_CURVE, pFlame.getMixerBBCurve());
        break;
      case FULL:
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_RR_CURVE, pFlame.getMixerRRCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_RG_CURVE, pFlame.getMixerRGCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_RB_CURVE, pFlame.getMixerRBCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_GR_CURVE, pFlame.getMixerGRCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_GG_CURVE, pFlame.getMixerGGCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_GB_CURVE, pFlame.getMixerGBCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_BR_CURVE, pFlame.getMixerBRCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_BG_CURVE, pFlame.getMixerBGCurve());
        writeMotionCurve(xb, attrList, AbstractFlameReader.ATTR_CHANNEL_MIXER_BB_CURVE, pFlame.getMixerBBCurve());
        break;
      default:
        break;
    }

    return attrList;
  }

  protected void addPalette(SimpleXMLBuilder xb, Layer layer) {
    // Palette
    {
      RGBPalette palette = layer.getPalette();
      xb.beginElement("palette",
          xb.createAttr("count", palette.getSize()),
          xb.createAttr("format", "RGB"));
      StringBuilder rgb = new StringBuilder();
      for (int i = 0; i < palette.getSize(); i++) {
        String hs;
        hs = Integer.toHexString(palette.getColor(i).getRed()).toUpperCase();
        rgb.append(hs.length() > 1 ? hs : "0" + hs);
        hs = Integer.toHexString(palette.getColor(i).getGreen()).toUpperCase();
        rgb.append(hs.length() > 1 ? hs : "0" + hs);
        hs = Integer.toHexString(palette.getColor(i).getBlue()).toUpperCase();
        rgb.append(hs.length() > 1 ? hs : "0" + hs);
        if ((i + 1) % 12 == 0) {
          rgb.append("\n");
        }
      }
      xb.addContent(rgb.toString());
      xb.endElement("palette");
    }
  }

  protected void writeMotionCurves(Object source, SimpleXMLBuilder xb, List<SimpleXMLBuilder.Attribute<?>> attrList, String pNamePrefix, List<String> pNameBlackList) throws Exception {
    for (MotionCurveAttribute attribute : AnimationService.getAllMotionCurves(source)) {
      MotionCurve curve = attribute.getMotionCurve();
      String name = pNamePrefix == null ? attribute.getName() : pNamePrefix + attribute.getName();
      if (!pNameBlackList.contains(name)) {
        writeMotionCurve(xb, attrList, name, curve);
      }
    }
  }

  private void writeMotionCurve(SimpleXMLBuilder xb, List<SimpleXMLBuilder.Attribute<?>> attrList, String pPropertyname, MotionCurve curve) {
    if (!curve.isEmpty()) {
      String namePrefix = pPropertyname + "_";
      addMotionCurveAttributes(xb, attrList, namePrefix, curve);
    }
  }

  public static void addMotionCurveAttributes(SimpleXMLBuilder xb, List<SimpleXMLBuilder.Attribute<?>> attrList, String namePrefix, MotionCurve curve) {
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_ENABLED, String.valueOf(curve.isEnabled())));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_XMIN, curve.getViewXMin()));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_XMAX, curve.getViewXMax()));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_YMIN, curve.getViewYMin()));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_YMAX, curve.getViewYMax()));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_INTERPOLATION, curve.getInterpolation().toString()));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_SELECTED_IDX, curve.getSelectedIdx()));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_LOCKED, String.valueOf(curve.isLocked())));
    attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_POINT_COUNT, curve.getX().length));
    for (int i = 0; i < curve.getX().length; i++) {
      attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_X + i, curve.getX()[i]));
      attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_Y + i, curve.getY()[i]));
    }
    if (curve.getParent() != null) {
      String parentName = "parent" + curve.getParent().hashCode() + "Curve";
      attrList.add(xb.createAttr(namePrefix + AbstractFlameReader.CURVE_ATTR_PARENT_CURVE, parentName));
      addMotionCurveAttributes(xb, attrList, parentName + "_", curve.getParent());
    }
  }

  private static final List<String> flameAttrMotionCurveBlackList;

  static {
    flameAttrMotionCurveBlackList = new ArrayList<String>();
    flameAttrMotionCurveBlackList.add("mixerRRCurve");
    flameAttrMotionCurveBlackList.add("mixerRGCurve");
    flameAttrMotionCurveBlackList.add("mixerRBCurve");
    flameAttrMotionCurveBlackList.add("mixerGRCurve");
    flameAttrMotionCurveBlackList.add("mixerGGCurve");
    flameAttrMotionCurveBlackList.add("mixerGBCurve");
    flameAttrMotionCurveBlackList.add("mixerBRCurve");
    flameAttrMotionCurveBlackList.add("mixerBGCurve");
    flameAttrMotionCurveBlackList.add("mixerBBCurve");
  }
}
