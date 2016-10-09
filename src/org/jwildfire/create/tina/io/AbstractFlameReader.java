package org.jwildfire.create.tina.io;

import java.util.List;
import java.util.Map;

import org.jwildfire.base.Prefs;
import org.jwildfire.base.Tools;
import org.jwildfire.base.Tools.XMLAttribute;
import org.jwildfire.base.Tools.XMLAttributes;
import org.jwildfire.base.mathlib.MathLib;
import org.jwildfire.create.tina.animate.AnimationService;
import org.jwildfire.create.tina.animate.AnimationService.MotionCurveAttribute;
import org.jwildfire.create.tina.base.DrawMode;
import org.jwildfire.create.tina.base.Flame;
import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.PostSymmetryType;
import org.jwildfire.create.tina.base.Stereo3dColor;
import org.jwildfire.create.tina.base.Stereo3dMode;
import org.jwildfire.create.tina.base.Stereo3dPreview;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.motion.MotionCurve;
import org.jwildfire.create.tina.base.solidrender.LightDiffFuncPreset;
import org.jwildfire.create.tina.base.solidrender.MaterialSettings;
import org.jwildfire.create.tina.base.solidrender.PointLight;
import org.jwildfire.create.tina.base.solidrender.ReflectionMapping;
import org.jwildfire.create.tina.base.solidrender.ShadowType;
import org.jwildfire.create.tina.render.ChannelMixerMode;
import org.jwildfire.create.tina.render.dof.DOFBlurShape;
import org.jwildfire.create.tina.render.dof.DOFBlurShapeType;
import org.jwildfire.create.tina.render.filter.FilterKernelType;
import org.jwildfire.create.tina.variation.RessourceName;
import org.jwildfire.create.tina.variation.Variation;
import org.jwildfire.create.tina.variation.VariationFunc;
import org.jwildfire.create.tina.variation.VariationFuncList;
import org.jwildfire.envelope.Envelope.Interpolation;

public class AbstractFlameReader {
  protected final Prefs prefs;

  public static final String ATTR_NAME = "name";
  public static final String ATTR_LAYER_NAME = "layer_name";
  public static final String ATTR_GRADIENT_MAP = "gradient_map";
  public static final String ATTR_GRADIENT_MAP_HOFFSET = "gradient_map_hoffset";
  public static final String ATTR_GRADIENT_MAP_HSCALE = "gradient_map_hscale";
  public static final String ATTR_GRADIENT_MAP_VOFFSET = "gradient_map_voffset";
  public static final String ATTR_GRADIENT_MAP_VSCALE = "gradient_map_vscale";
  public static final String ATTR_GRADIENT_MAP_LCOLOR_ADD = "gradient_map_lcolor_add";
  public static final String ATTR_GRADIENT_MAP_LCOLOR_SCALE = "gradient_map_lcolor_scale";
  public static final String ATTR_BACKGROUND_IMAGE = "background_image";
  public static final String ATTR_SMOOTH_GRADIENT = "smooth_gradient";
  public static final String ATTR_SIZE = "size";
  public static final String ATTR_CENTER = "center";
  public static final String ATTR_SCALE = "scale";
  public static final String ATTR_ROTATE = "rotate";
  public static final String ATTR_FILTER = "filter";
  public static final String ATTR_SPATIAL_OVERSAMPLE = "oversample";
  public static final String ATTR_COLOR_OVERSAMPLE = "color_oversample";
  public static final String ATTR_SAMPLE_JITTERING = "sample_jittering";
  public static final String ATTR_POST_NOISE_FILTER = "post_noise_filter";
  public static final String ATTR_POST_NOISE_FILTER_THRESHOLD = "post_noise_filter_threshold";
  public static final String ATTR_FILTER_KERNEL = "filter_kernel";
  public static final String ATTR_QUALITY = "quality";
  public static final String ATTR_BACKGROUND = "background";
  public static final String ATTR_BG_TRANSPARENCY = "bg_transparency";
  public static final String ATTR_BRIGHTNESS = "brightness";
  public static final String ATTR_SATURATION = "saturation";
  public static final String ATTR_GAMMA = "gamma";
  public static final String ATTR_GAMMA_THRESHOLD = "gamma_threshold";
  public static final String ATTR_VIBRANCY = "vibrancy";
  public static final String ATTR_CONTRAST = "contrast";
  public static final String ATTR_WHITE_LEVEL = "white_level";
  public static final String ATTR_INDEX = "index";
  public static final String ATTR_RGB = "rgb";
  public static final String ATTR_CAM_PITCH = "cam_pitch";
  public static final String ATTR_CAM_YAW = "cam_yaw";
  public static final String ATTR_CAM_PERSP = "cam_persp";
  public static final String ATTR_CAM_PERSPECTIVE = "cam_perspective"; // old version
  public static final String ATTR_CAM_POS_X = "cam_pos_x";
  public static final String ATTR_CAM_POS_Y = "cam_pos_y";
  public static final String ATTR_CAM_POS_Z = "cam_pos_z";
  public static final String ATTR_CAM_ZPOS = "cam_zpos";
  public static final String ATTR_CAM_XFOCUS = "cam_xfocus";
  public static final String ATTR_CAM_YFOCUS = "cam_yfocus";
  public static final String ATTR_CAM_ZFOCUS = "cam_zfocus";
  public static final String ATTR_CAM_ZDIMISH = "cam_zdimish";
  public static final String ATTR_CAM_DOF = "cam_dof";
  public static final String ATTR_CAM_DOF_AREA = "cam_dof_area";
  public static final String ATTR_CAM_DOF_EXPONENT = "cam_dof_exponent";
  public static final String ATTR_NEW_DOF = "new_dof";
  public static final String ATTR_CAM_DOF_SHAPE = "cam_dof_shape";
  public static final String ATTR_CAM_DOF_SCALE = "cam_dof_scale";
  public static final String ATTR_CAM_DOF_ROTATE = "cam_dof_rotate";
  public static final String ATTR_CAM_DOF_FADE = "cam_dof_fade";
  public static final String ATTR_CAM_DOF_PARAM1 = "cam_dof_param1";
  public static final String ATTR_CAM_DOF_PARAM2 = "cam_dof_param2";
  public static final String ATTR_CAM_DOF_PARAM3 = "cam_dof_param3";
  public static final String ATTR_CAM_DOF_PARAM4 = "cam_dof_param4";
  public static final String ATTR_CAM_DOF_PARAM5 = "cam_dof_param5";
  public static final String ATTR_CAM_DOF_PARAM6 = "cam_dof_param6";
  public static final String ATTR_CAM_ZOOM = "cam_zoom";
  public static final String ATTR_NEW_LINEAR = "new_linear";
  public static final String ATTR_PRESERVE_Z = "preserve_z";
  public static final String ATTR_RESOLUTION_PROFILE = "resolution_profile";
  public static final String ATTR_QUALITY_PROFILE = "quality_profile";
  public static final String ATTR_SHADING_DISTANCE_COLOR_RADIUS = "shading_distanceColorRadius";
  public static final String ATTR_SHADING_DISTANCE_COLOR_SCALE = "shading_distanceColorScale";
  public static final String ATTR_SHADING_DISTANCE_COLOR_EXPONENT = "shading_distanceColorExponent";
  public static final String ATTR_SHADING_DISTANCE_COLOR_OFFSETX = "shading_distanceColorOffsetX";
  public static final String ATTR_SHADING_DISTANCE_COLOR_OFFSETY = "shading_distanceColorOffsetY";
  public static final String ATTR_SHADING_DISTANCE_COLOR_OFFSETZ = "shading_distanceColorOffsetZ";
  public static final String ATTR_SHADING_DISTANCE_COLOR_STYLE = "shading_distanceColorStyle";
  public static final String ATTR_SHADING_DISTANCE_COLOR_COORDINATE = "shading_distanceColorCoordinate";
  public static final String ATTR_SHADING_DISTANCE_COLOR_SHIFT = "shading_distanceColorShift";
  public static final String ATTR_MOTIONBLUR_LENGTH = "motion_blur_length";
  public static final String ATTR_MOTIONBLUR_TIMESTEP = "motion_blur_timestep";
  public static final String ATTR_MOTIONBLUR_DECAY = "motion_blur_decay";
  public static final String ATTR_POST_SYMMETRY_TYPE = "post_symmetry_type";
  public static final String ATTR_POST_SYMMETRY_ORDER = "post_symmetry_order";
  public static final String ATTR_POST_SYMMETRY_CENTREX = "post_symmetry_centre_x";
  public static final String ATTR_POST_SYMMETRY_CENTREY = "post_symmetry_centre_y";
  public static final String ATTR_POST_SYMMETRY_DISTANCE = "post_symmetry_distance";
  public static final String ATTR_POST_SYMMETRY_ROTATION = "post_symmetry_rotation";
  public static final String ATTR_STEREO3D_MODE = "stereo3d_mode";
  public static final String ATTR_STEREO3D_ANGLE = "stereo3d_angle";
  public static final String ATTR_STEREO3D_EYE_DIST = "stereo3d_eye_dist";
  public static final String ATTR_STEREO3D_FOCAL_OFFSET = "stereo3d_focal_offset";
  public static final String ATTR_STEREO3D_LEFT_EYE_COLOR = "stereo3d_left_eye_color";
  public static final String ATTR_STEREO3D_RIGHT_EYE_COLOR = "stereo3d_right_eye_color";
  public static final String ATTR_STEREO3D_INTERPOLATED_IMAGE_COUNT = "stereo3d_interpolated_image_count";
  public static final String ATTR_STEREO3D_PREVIEW = "stereo3d_preview";
  public static final String ATTR_STEREO3D_SWAP_SIDES = "stereo3d_swap_sides";
  public static final String ATTR_FRAME_COUNT = "frame_count";
  public static final String ATTR_FRAME = "frame";
  public static final String ATTR_FPS = "fps";

  public static final String ATTR_SLD_RENDER_ENABLED = "sld_render_enabled";
  public static final String ATTR_SLD_RENDER_AO_ENABLED = "sld_render_ao_enabled";
  public static final String ATTR_SLD_RENDER_AO_INTENSITY = "sld_render_ao_intensity";
  public static final String ATTR_SLD_RENDER_AO_SEARCH_RADIUS = "sld_render_ao_search_radius";
  public static final String ATTR_SLD_RENDER_AO_BLUR_RADIUS = "sld_render_ao_blur_radius";
  public static final String ATTR_SLD_RENDER_AO_RADIUS_SAMPLES = "sld_render_ao_radius_samples";
  public static final String ATTR_SLD_RENDER_AO_AZIMUTH_SAMPLES = "sld_render_ao_azimuth_samples";
  public static final String ATTR_SLD_RENDER_AO_FALLOFF = "sld_render_ao_falloff";
  public static final String ATTR_SLD_RENDER_AO_AFFECT_DIFFUSE = "sld_render_ao_affect_diffuse";
  public static final String ATTR_SLD_RENDER_MATERIAL_COUNT = "sld_render_material_count";
  public static final String ATTR_SLD_RENDER_LIGHT_COUNT = "sld_render_ligtht_count";
  public static final String ATTR_SLD_RENDER_SHADOW_TYPE = "sld_render_shadow_type";
  public static final String ATTR_SLD_RENDER_SHADOW_SMOOTH_RADIUS = "sld_render_shadow_smooth_radius";
  public static final String ATTR_SLD_RENDER_SHADOWMAP_SIZE = "sld_render_shadowmap_size";
  public static final String ATTR_SLD_RENDER_SHADOWMAP_BIAS = "sld_render_shadowmap_bias";

  public static final String ATTR_SLD_RENDER_MATERIAL_DIFFUSE = "sld_render_material_diffuse";
  public static final String ATTR_SLD_RENDER_MATERIAL_AMBIENT = "sld_render_material_ambient";
  public static final String ATTR_SLD_RENDER_MATERIAL_PHONG = "sld_render_material_phong";
  public static final String ATTR_SLD_RENDER_MATERIAL_PHONG_SIZE = "sld_render_material_phong_size";
  public static final String ATTR_SLD_RENDER_MATERIAL_PHONG_RED = "sld_render_material_phong_red";
  public static final String ATTR_SLD_RENDER_MATERIAL_PHONG_GREEN = "sld_render_material_phong_green";
  public static final String ATTR_SLD_RENDER_MATERIAL_PHONG_BLUE = "sld_render_material_phong_blue";
  public static final String ATTR_SLD_RENDER_MATERIAL_LIGHT_DIFF_FUNC = "sld_render_material_light_diif_func";
  public static final String ATTR_SLD_RENDER_MATERIAL_REFL_MAP_INTENSITY = "sld_render_material_refl_map_intensity";
  public static final String ATTR_SLD_RENDER_MATERIAL_REFL_MAP_FILENAME = "sld_render_material_refl_map_filename";
  public static final String ATTR_SLD_RENDER_MATERIAL_REFL_MAPPING = "sld_render_material_refl_mappping";

  public static final String ATTR_SLD_RENDER_LIGHT_X = "sld_render_light_x";
  public static final String ATTR_SLD_RENDER_LIGHT_Y = "sld_render_light_y";
  public static final String ATTR_SLD_RENDER_LIGHT_Z = "sld_render_light_z";
  public static final String ATTR_SLD_RENDER_LIGHT_INTENSITY = "sld_render_light_intensity";
  public static final String ATTR_SLD_RENDER_LIGHT_RED = "sld_render_light_red";
  public static final String ATTR_SLD_RENDER_LIGHT_GREEN = "sld_render_light_green";
  public static final String ATTR_SLD_RENDER_LIGHT_BLUE = "sld_render_light_blue";
  public static final String ATTR_SLD_RENDER_LIGHT_SHADOWS = "sld_render_light_shadows";
  public static final String ATTR_SLD_RENDER_LIGHT_SHADOW_INTENSITY = "sld_render_light_shadow_intensity";

  public static final String CURVE_ATTR_ENABLED = "enabled";
  public static final String CURVE_ATTR_VIEW_XMIN = "view_xmin";
  public static final String CURVE_ATTR_VIEW_XMAX = "view_xmax";
  public static final String CURVE_ATTR_VIEW_YMIN = "view_ymin";
  public static final String CURVE_ATTR_VIEW_YMAX = "view_ymax";
  public static final String CURVE_ATTR_PARENT_CURVE = "parent_curve";
  public static final String CURVE_ATTR_INTERPOLATION = "interpolation";
  public static final String CURVE_ATTR_SELECTED_IDX = "selected_idx";
  public static final String CURVE_ATTR_LOCKED = "locked";
  public static final String CURVE_ATTR_POINT_COUNT = "point_count";
  public static final String CURVE_ATTR_X = "x";
  public static final String CURVE_ATTR_Y = "y";

  protected AbstractFlameReader(Prefs pPrefs) {
    prefs = pPrefs;
  }

  protected XMLAttributes parseFlameAttributes(Flame pFlame, String pXML) {
    XMLAttributes atts = Tools.parseAttributes(pXML);
    String hs;
    if ((hs = atts.get(ATTR_NAME)) != null) {
      pFlame.setName(hs);
    }
    if ((hs = atts.get(ATTR_BACKGROUND_IMAGE)) != null) {
      pFlame.setBGImageFilename(hs);
    }
    if ((hs = atts.get(ATTR_LAYER_NAME)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setName(hs);
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapFilename(hs);
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP_HOFFSET)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapHorizOffset(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP_HSCALE)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapHorizScale(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP_VOFFSET)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapVertOffset(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP_VSCALE)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapVertScale(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP_LCOLOR_ADD)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapLocalColorAdd(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_GRADIENT_MAP_LCOLOR_SCALE)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setGradientMapLocalColorScale(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_SMOOTH_GRADIENT)) != null && pFlame.getLayers().size() == 1) {
      pFlame.getFirstLayer().setSmoothGradient("1".equals(hs));
    }
    if ((hs = atts.get(ATTR_SIZE)) != null) {
      String s[] = hs.split(" ");
      pFlame.setWidth(Integer.parseInt(s[0]));
      pFlame.setHeight(Integer.parseInt(s[1]));
    }
    if ((hs = atts.get(ATTR_CENTER)) != null) {
      String s[] = hs.split(" ");
      pFlame.setCentreX(Double.parseDouble(s[0]));
      pFlame.setCentreY(Double.parseDouble(s[1]));
    }
    if ((hs = atts.get(ATTR_SCALE)) != null) {
      pFlame.setPixelsPerUnit(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_ROTATE)) != null) {
      //      pFlame.setCamRoll(-Double.parseDouble(hs) * 180.0 / Math.PI);
      pFlame.setCamRoll(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_FILTER)) != null) {
      pFlame.setSpatialFilterRadius(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_FILTER_KERNEL)) != null) {
      try {
        FilterKernelType kernel = FilterKernelType.valueOf(hs);
        pFlame.setSpatialFilterKernel(kernel);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_SPATIAL_OVERSAMPLE)) != null) {
      pFlame.setSpatialOversampling(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_COLOR_OVERSAMPLE)) != null) {
      pFlame.setColorOversampling(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_SAMPLE_JITTERING)) != null) {
      pFlame.setSampleJittering(Integer.parseInt(hs) == 1);
    }
    if ((hs = atts.get(ATTR_POST_NOISE_FILTER)) != null) {
      pFlame.setPostNoiseFilter(Integer.parseInt(hs) == 1);
    }
    if ((hs = atts.get(ATTR_POST_NOISE_FILTER_THRESHOLD)) != null) {
      pFlame.setPostNoiseFilterThreshold(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_QUALITY)) != null) {
      pFlame.setSampleDensity(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_BACKGROUND)) != null) {
      String s[] = hs.split(" ");
      pFlame.setBGColorRed(Tools.roundColor(255.0 * Double.parseDouble(s[0])));
      pFlame.setBGColorGreen(Tools.roundColor(255.0 * Double.parseDouble(s[1])));
      pFlame.setBGColorBlue(Tools.roundColor(255.0 * Double.parseDouble(s[2])));
    }
    if ((hs = atts.get(ATTR_BRIGHTNESS)) != null) {
      pFlame.setBrightness(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_SATURATION)) != null) {
      pFlame.setSaturation(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_BG_TRANSPARENCY)) != null) {
      pFlame.setBGTransparency(Integer.parseInt(hs) == 1);
    }
    else {
      pFlame.setBGTransparency(prefs.isTinaDefaultBGTransparency());
    }
    if ((hs = atts.get(ATTR_GAMMA)) != null) {
      pFlame.setGamma(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_GAMMA_THRESHOLD)) != null) {
      pFlame.setGammaThreshold(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_VIBRANCY)) != null) {
      pFlame.setVibrancy(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CONTRAST)) != null) {
      pFlame.setContrast(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_WHITE_LEVEL)) != null) {
      pFlame.setWhiteLevel(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_PERSP)) != null) {
      pFlame.setCamPerspective(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_PERSPECTIVE)) != null) {
      pFlame.setCamPerspective(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_ZPOS)) != null) {
      pFlame.setCamZ(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_POS_X)) != null) {
      pFlame.setCamPosX(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_POS_Y)) != null) {
      pFlame.setCamPosY(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_POS_Z)) != null) {
      pFlame.setCamPosZ(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_XFOCUS)) != null) {
      pFlame.setFocusX(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_YFOCUS)) != null) {
      pFlame.setFocusY(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_ZFOCUS)) != null) {
      pFlame.setFocusZ(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_ZDIMISH)) != null) {
      pFlame.setDimishZ(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_DOF)) != null) {
      pFlame.setCamDOF(Double.parseDouble(hs));
    }

    if ((hs = atts.get(ATTR_CAM_DOF_SHAPE)) != null) {
      try {
        pFlame.setCamDOFShape(DOFBlurShapeType.valueOf(hs));
      }
      catch (Exception ex) {
        pFlame.setCamDOFShape(DOFBlurShapeType.BUBBLE);
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_CAM_DOF_SCALE)) != null) {
      pFlame.setCamDOFScale(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_DOF_ROTATE)) != null) {
      pFlame.setCamDOFAngle(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_DOF_FADE)) != null) {
      pFlame.setCamDOFFade(Double.parseDouble(hs));
    }
    {
      DOFBlurShape shape = pFlame.getCamDOFShape().getDOFBlurShape();
      List<String> paramNames = shape.getParamNames();
      if (paramNames.size() > 0 && (hs = atts.get(ATTR_CAM_DOF_PARAM1)) != null) {
        pFlame.setCamDOFParam1(Double.parseDouble(hs));
      }
      if (paramNames.size() > 1 && (hs = atts.get(ATTR_CAM_DOF_PARAM2)) != null) {
        pFlame.setCamDOFParam2(Double.parseDouble(hs));
      }
      if (paramNames.size() > 2 && (hs = atts.get(ATTR_CAM_DOF_PARAM3)) != null) {
        pFlame.setCamDOFParam3(Double.parseDouble(hs));
      }
      if (paramNames.size() > 3 && (hs = atts.get(ATTR_CAM_DOF_PARAM4)) != null) {
        pFlame.setCamDOFParam4(Double.parseDouble(hs));
      }
      if (paramNames.size() > 4 && (hs = atts.get(ATTR_CAM_DOF_PARAM5)) != null) {
        pFlame.setCamDOFParam5(Double.parseDouble(hs));
      }
      if (paramNames.size() > 5 && (hs = atts.get(ATTR_CAM_DOF_PARAM6)) != null) {
        pFlame.setCamDOFParam6(Double.parseDouble(hs));
      }
    }

    if ((hs = atts.get(ATTR_CAM_DOF_AREA)) != null) {
      pFlame.setCamDOFArea(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_DOF_EXPONENT)) != null) {
      pFlame.setCamDOFExponent(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_CAM_PITCH)) != null) {
      pFlame.setCamPitch(Double.parseDouble(hs) * 180.0 / Math.PI);
    }
    if ((hs = atts.get(ATTR_CAM_YAW)) != null) {
      pFlame.setCamYaw(Double.parseDouble(hs) * 180.0 / Math.PI);
    }
    if ((hs = atts.get(ATTR_CAM_ZOOM)) != null) {
      pFlame.setCamZoom(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_NEW_DOF)) != null) {
      pFlame.setNewCamDOF("1".equals(hs));
    }
    // preserve-z
    if ((hs = atts.get(ATTR_PRESERVE_Z)) != null) {
      pFlame.setPreserveZ("1".equals(hs));
    }
    // profiles
    if ((hs = atts.get(ATTR_RESOLUTION_PROFILE)) != null) {
      pFlame.setResolutionProfile(hs);
    }
    if ((hs = atts.get(ATTR_QUALITY_PROFILE)) != null) {
      pFlame.setQualityProfile(hs);
    }
    if ((hs = atts.get(ATTR_NEW_LINEAR)) != null) {
      pFlame.setPreserveZ(hs.length() > 0 && Integer.parseInt(hs) == 1);
    }

    if ((hs = atts.get(ATTR_ANTIALIAS_AMOUNT)) != null) {
      pFlame.setAntialiasAmount(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_ANTIALIAS_RADIUS)) != null) {
      pFlame.setAntialiasRadius(Double.parseDouble(hs));
    }

    if ((hs = atts.get(ATTR_MOTIONBLUR_LENGTH)) != null) {
      int blurLen = Integer.parseInt(hs);
      pFlame.setMotionBlurLength(blurLen);
    }
    if ((hs = atts.get(ATTR_MOTIONBLUR_TIMESTEP)) != null) {
      double timestep = Double.parseDouble(hs);
      pFlame.setMotionBlurTimeStep(timestep);
    }
    if ((hs = atts.get(ATTR_MOTIONBLUR_DECAY)) != null) {
      pFlame.setMotionBlurDecay(Double.parseDouble(hs));
    }

    if ((hs = atts.get(ATTR_POST_SYMMETRY_TYPE)) != null) {
      try {
        pFlame.setPostSymmetryType(PostSymmetryType.valueOf(hs));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_POST_SYMMETRY_ORDER)) != null) {
      pFlame.setPostSymmetryOrder(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_POST_SYMMETRY_CENTREX)) != null) {
      pFlame.setPostSymmetryCentreX(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_POST_SYMMETRY_CENTREY)) != null) {
      pFlame.setPostSymmetryCentreY(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_POST_SYMMETRY_DISTANCE)) != null) {
      pFlame.setPostSymmetryDistance(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_POST_SYMMETRY_ROTATION)) != null) {
      pFlame.setPostSymmetryRotation(Double.parseDouble(hs));
    }

    if ((hs = atts.get(ATTR_STEREO3D_MODE)) != null) {
      try {
        pFlame.setStereo3dMode(Stereo3dMode.valueOf(hs));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_STEREO3D_ANGLE)) != null) {
      pFlame.setStereo3dAngle(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_STEREO3D_EYE_DIST)) != null) {
      pFlame.setStereo3dEyeDist(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_STEREO3D_FOCAL_OFFSET)) != null) {
      pFlame.setStereo3dFocalOffset(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_STEREO3D_LEFT_EYE_COLOR)) != null) {
      try {
        pFlame.setStereo3dLeftEyeColor(Stereo3dColor.valueOf(hs));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_STEREO3D_RIGHT_EYE_COLOR)) != null) {
      try {
        pFlame.setStereo3dRightEyeColor(Stereo3dColor.valueOf(hs));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_STEREO3D_INTERPOLATED_IMAGE_COUNT)) != null) {
      pFlame.setStereo3dInterpolatedImageCount(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_STEREO3D_PREVIEW)) != null) {
      try {
        pFlame.setStereo3dPreview(Stereo3dPreview.valueOf(hs));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if ((hs = atts.get(ATTR_STEREO3D_SWAP_SIDES)) != null) {
      pFlame.setStereo3dSwapSides(Integer.parseInt(hs) == 1);
    }

    if ((hs = atts.get(ATTR_FRAME)) != null) {
      pFlame.setFrame(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_FRAME_COUNT)) != null) {
      pFlame.setFrameCount(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_FPS)) != null) {
      int fps = Integer.parseInt(hs);
      if (fps >= 1)
        pFlame.setFps(fps);
    }

    if ((hs = atts.get(ATTR_POSTBLUR_RADIUS)) != null) {
      pFlame.setPostBlurRadius(Integer.parseInt(hs));
    }
    if ((hs = atts.get(ATTR_POSTBLUR_FADE)) != null) {
      pFlame.setPostBlurFade(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_POSTBLUR_FALLOFF)) != null) {
      pFlame.setPostBlurFallOff(Double.parseDouble(hs));
    }

    if ((hs = atts.get(ATTR_ZBUFFER_SCALE)) != null) {
      pFlame.setZBufferScale(Double.parseDouble(hs));
    }

    if ((hs = atts.get(ATTR_SLD_RENDER_ENABLED)) != null) {
      pFlame.getSolidRenderSettings().setSolidRenderingEnabled(Integer.parseInt(hs) == 1);
    }
    else {
      pFlame.getSolidRenderSettings().setSolidRenderingEnabled(false);
    }
    if (pFlame.getSolidRenderSettings().isSolidRenderingEnabled()) {
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_ENABLED)) != null) {
        pFlame.getSolidRenderSettings().setAoEnabled(Integer.parseInt(hs) == 1);
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_INTENSITY)) != null) {
        pFlame.getSolidRenderSettings().setAoIntensity(Double.parseDouble(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_SEARCH_RADIUS)) != null) {
        pFlame.getSolidRenderSettings().setAoSearchRadius(Double.parseDouble(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_BLUR_RADIUS)) != null) {
        pFlame.getSolidRenderSettings().setAoBlurRadius(Double.parseDouble(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_RADIUS_SAMPLES)) != null) {
        pFlame.getSolidRenderSettings().setAoRadiusSamples(Integer.parseInt(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_AZIMUTH_SAMPLES)) != null) {
        pFlame.getSolidRenderSettings().setAoAzimuthSamples(Integer.parseInt(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_FALLOFF)) != null) {
        pFlame.getSolidRenderSettings().setAoFalloff(Double.parseDouble(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_AO_AFFECT_DIFFUSE)) != null) {
        pFlame.getSolidRenderSettings().setAoAffectDiffuse(Double.parseDouble(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_SHADOW_TYPE)) != null) {
        try {
          pFlame.getSolidRenderSettings().setShadowType(ShadowType.valueOf(hs));
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_SHADOW_SMOOTH_RADIUS)) != null) {
        pFlame.getSolidRenderSettings().setShadowSmoothRadius(Double.parseDouble(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_SHADOWMAP_SIZE)) != null) {
        pFlame.getSolidRenderSettings().setShadowmapSize(Integer.parseInt(hs));
      }
      if ((hs = atts.get(ATTR_SLD_RENDER_SHADOWMAP_BIAS)) != null) {
        pFlame.getSolidRenderSettings().setShadowmapBias(Double.parseDouble(hs));
      }

      if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_COUNT)) != null) {
        int materialCount = Integer.parseInt(hs);
        pFlame.getSolidRenderSettings().getMaterials().clear();
        for (int i = 0; i < materialCount; i++) {
          MaterialSettings material = new MaterialSettings();
          pFlame.getSolidRenderSettings().getMaterials().add(material);
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_DIFFUSE + i)) != null) {
            material.setDiffuse(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_AMBIENT + i)) != null) {
            material.setAmbient(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_PHONG + i)) != null) {
            material.setPhong(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_PHONG_SIZE + i)) != null) {
            material.setPhongSize(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_PHONG_RED + i)) != null) {
            material.setPhongRed(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_PHONG_GREEN + i)) != null) {
            material.setPhongGreen(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_PHONG_BLUE + i)) != null) {
            material.setPhongBlue(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_LIGHT_DIFF_FUNC + i)) != null) {
            try {
              material.setLightDiffFunc(LightDiffFuncPreset.valueOf(hs));
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_REFL_MAP_INTENSITY + i)) != null) {
            material.setReflMapIntensity(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_REFL_MAP_FILENAME + i)) != null) {
            material.setReflMapFilename(hs);
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_MATERIAL_REFL_MAPPING + i)) != null) {
            try {
              material.setReflectionMapping(ReflectionMapping.valueOf(hs));
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
          }

        }
      }

      if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_COUNT)) != null) {
        int lightCount = Integer.parseInt(hs);
        pFlame.getSolidRenderSettings().getLights().clear();
        for (int i = 0; i < lightCount; i++) {
          PointLight light = new PointLight();
          pFlame.getSolidRenderSettings().getLights().add(light);
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_X + i)) != null) {
            light.setX(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_Y + i)) != null) {
            light.setY(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_Z + i)) != null) {
            light.setZ(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_INTENSITY + i)) != null) {
            light.setIntensity(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_SHADOW_INTENSITY + i)) != null) {
            light.setShadowIntensity(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_RED + i)) != null) {
            light.setRed(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_GREEN + i)) != null) {
            light.setGreen(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_BLUE + i)) != null) {
            light.setBlue(Double.parseDouble(hs));
          }
          if ((hs = atts.get(ATTR_SLD_RENDER_LIGHT_SHADOWS + i)) != null) {
            light.setCastShadows(Integer.parseInt(hs) == 1);
          }
        }
      }
    }

    readMotionCurves(pFlame, atts, null);

    if ((hs = atts.get(ATTR_CHANNEL_MIXER_MODE)) != null) {
      try {
        pFlame.setChannelMixerMode(ChannelMixerMode.valueOf(hs));
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    switch (pFlame.getChannelMixerMode()) {
      case BRIGHTNESS:
        readMotionCurveAttributes(atts, pFlame.getMixerRRCurve(), ATTR_CHANNEL_MIXER_RR_CURVE + "_");
        break;
      case RGB:
        readMotionCurveAttributes(atts, pFlame.getMixerRRCurve(), ATTR_CHANNEL_MIXER_RR_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerGGCurve(), ATTR_CHANNEL_MIXER_GG_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerBBCurve(), ATTR_CHANNEL_MIXER_BB_CURVE + "_");
        break;
      case FULL:
        readMotionCurveAttributes(atts, pFlame.getMixerRRCurve(), ATTR_CHANNEL_MIXER_RR_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerRGCurve(), ATTR_CHANNEL_MIXER_RG_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerRBCurve(), ATTR_CHANNEL_MIXER_RB_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerGRCurve(), ATTR_CHANNEL_MIXER_GR_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerGGCurve(), ATTR_CHANNEL_MIXER_GG_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerGBCurve(), ATTR_CHANNEL_MIXER_GB_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerBRCurve(), ATTR_CHANNEL_MIXER_BR_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerBGCurve(), ATTR_CHANNEL_MIXER_BG_CURVE + "_");
        readMotionCurveAttributes(atts, pFlame.getMixerBBCurve(), ATTR_CHANNEL_MIXER_BB_CURVE + "_");
        break;
      default:
        break;
    }
    return atts;
  }

  public static final String ATTR_WEIGHT = "weight";
  public static final String ATTR_COLOR = "color";
  public static final String ATTR_OPACITY = "opacity";
  public static final String ATTR_XY_COEFS = "coefs";
  public static final String ATTR_XY_POST = "post";
  public static final String ATTR_YZ_COEFS = "yzCoefs";
  public static final String ATTR_YZ_POST = "yzPost";
  public static final String ATTR_ZX_COEFS = "zxCoefs";
  public static final String ATTR_ZX_POST = "zxPost";
  public static final String ATTR_CHAOS = "chaos";
  public static final String ATTR_SYMMETRY = "symmetry";
  public static final String ATTR_MATERIAL = "material";
  public static final String ATTR_MATERIAL_SPEED = "material_speed";
  public static final String ATTR_MOD_GAMMA = "mod_gamma";
  public static final String ATTR_MOD_GAMMA_SPEED = "mod_gamma_speed";
  public static final String ATTR_MOD_CONTRAST = "mod_contrast";
  public static final String ATTR_MOD_CONTRAST_SPEED = "mod_contrast_speed";
  public static final String ATTR_MOD_SATURATION = "mod_saturation";
  public static final String ATTR_MOD_SATURATION_SPEED = "mod_saturation_speed";
  public static final String ATTR_MOD_HUE = "mod_hue";
  public static final String ATTR_MOD_HUE_SPEED = "mod_hue_speed";
  public static final String ATTR_ANTIALIAS_AMOUNT = "antialias_amount";
  public static final String ATTR_ANTIALIAS_RADIUS = "antialias_radius";
  public static final String ATTR_VISIBLE = "visible";
  public static final String ATTR_CHANNEL_MIXER_MODE = "mixer_mode";
  public static final String ATTR_CHANNEL_MIXER_RR_CURVE = "mixer_rr_curve";
  public static final String ATTR_CHANNEL_MIXER_RG_CURVE = "mixer_rg_curve";
  public static final String ATTR_CHANNEL_MIXER_RB_CURVE = "mixer_rb_curve";
  public static final String ATTR_CHANNEL_MIXER_GR_CURVE = "mixer_gr_curve";
  public static final String ATTR_CHANNEL_MIXER_GG_CURVE = "mixer_gg_curve";
  public static final String ATTR_CHANNEL_MIXER_GB_CURVE = "mixer_gb_curve";
  public static final String ATTR_CHANNEL_MIXER_BR_CURVE = "mixer_br_curve";
  public static final String ATTR_CHANNEL_MIXER_BG_CURVE = "mixer_bg_curve";
  public static final String ATTR_CHANNEL_MIXER_BB_CURVE = "mixer_bb_curve";
  public static final String ATTR_FX_PRIORITY = "fx_priority";
  public static final String ATTR_SOLID_RENDERING = "solid_rendering";

  public static final String ATTR_POSTBLUR_RADIUS = "post_blur_radius";
  public static final String ATTR_POSTBLUR_FADE = "post_blur_fade";
  public static final String ATTR_POSTBLUR_FALLOFF = "post_blur_falloff";
  public static final String ATTR_ZBUFFER_SCALE = "zbuffer_scale";

  protected void parseXFormAttributes(Flame pFlame, XForm pXForm, String pXML) {
    XMLAttributes atts = Tools.parseAttributes(pXML);
    String hs;
    if ((hs = atts.get(ATTR_NAME)) != null) {
      pXForm.setName(hs);
    }
    if ((hs = atts.get(ATTR_WEIGHT)) != null) {
      pXForm.setWeight(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_COLOR)) != null) {
      pXForm.setColor(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MATERIAL)) != null) {
      pXForm.setMaterial(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MATERIAL_SPEED)) != null) {
      pXForm.setMaterialSpeed(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_GAMMA)) != null) {
      pXForm.setModGamma(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_GAMMA_SPEED)) != null) {
      pXForm.setModGammaSpeed(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_CONTRAST)) != null) {
      pXForm.setModContrast(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_CONTRAST_SPEED)) != null) {
      pXForm.setModContrastSpeed(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_SATURATION)) != null) {
      pXForm.setModSaturation(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_SATURATION_SPEED)) != null) {
      pXForm.setModSaturationSpeed(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_HUE)) != null) {
      pXForm.setModHue(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_MOD_HUE_SPEED)) != null) {
      pXForm.setModHueSpeed(Double.parseDouble(hs));
    }
    // legacy
    if ((hs = atts.get(ATTR_ANTIALIAS_AMOUNT)) != null) {
      double value = Double.parseDouble(hs);
      if (value > 0)
        pFlame.setAntialiasAmount(value);
    }
    // legacy
    if ((hs = atts.get(ATTR_ANTIALIAS_RADIUS)) != null) {
      double value = Double.parseDouble(hs);
      if (value > 0)
        pFlame.setAntialiasRadius(value);
    }

    if ((hs = atts.get(ATTR_OPACITY)) != null) {
      double opacity = Double.parseDouble(hs);
      pXForm.setOpacity(opacity);
      if (Math.abs(opacity) <= MathLib.EPSILON) {
        pXForm.setDrawMode(DrawMode.HIDDEN);
      }
      else if (Math.abs(opacity - 1.0) > MathLib.EPSILON) {
        pXForm.setDrawMode(DrawMode.OPAQUE);
      }
      else {
        pXForm.setDrawMode(DrawMode.NORMAL);
      }
    }
    if ((hs = atts.get(ATTR_SYMMETRY)) != null) {
      pXForm.setColorSymmetry(Double.parseDouble(hs));
    }
    if ((hs = atts.get(ATTR_XY_COEFS)) != null) {
      String s[] = hs.split(" ");
      pXForm.setXYCoeff00(Double.parseDouble(s[0]));
      pXForm.setXYCoeff01(Double.parseDouble(s[1]));
      pXForm.setXYCoeff10(Double.parseDouble(s[2]));
      pXForm.setXYCoeff11(Double.parseDouble(s[3]));
      pXForm.setXYCoeff20(Double.parseDouble(s[4]));
      pXForm.setXYCoeff21(Double.parseDouble(s[5]));
    }
    if ((hs = atts.get(ATTR_XY_POST)) != null) {
      String s[] = hs.split(" ");
      pXForm.setXYPostCoeff00(Double.parseDouble(s[0]));
      pXForm.setXYPostCoeff01(Double.parseDouble(s[1]));
      pXForm.setXYPostCoeff10(Double.parseDouble(s[2]));
      pXForm.setXYPostCoeff11(Double.parseDouble(s[3]));
      pXForm.setXYPostCoeff20(Double.parseDouble(s[4]));
      pXForm.setXYPostCoeff21(Double.parseDouble(s[5]));
    }
    if ((hs = atts.get(ATTR_YZ_COEFS)) != null) {
      String s[] = hs.split(" ");
      pXForm.setYZCoeff00(Double.parseDouble(s[0]));
      pXForm.setYZCoeff01(Double.parseDouble(s[1]));
      pXForm.setYZCoeff10(Double.parseDouble(s[2]));
      pXForm.setYZCoeff11(Double.parseDouble(s[3]));
      pXForm.setYZCoeff20(Double.parseDouble(s[4]));
      pXForm.setYZCoeff21(Double.parseDouble(s[5]));
    }
    if ((hs = atts.get(ATTR_YZ_POST)) != null) {
      String s[] = hs.split(" ");
      pXForm.setYZPostCoeff00(Double.parseDouble(s[0]));
      pXForm.setYZPostCoeff01(Double.parseDouble(s[1]));
      pXForm.setYZPostCoeff10(Double.parseDouble(s[2]));
      pXForm.setYZPostCoeff11(Double.parseDouble(s[3]));
      pXForm.setYZPostCoeff20(Double.parseDouble(s[4]));
      pXForm.setYZPostCoeff21(Double.parseDouble(s[5]));
    }
    if ((hs = atts.get(ATTR_ZX_COEFS)) != null) {
      String s[] = hs.split(" ");
      pXForm.setZXCoeff00(Double.parseDouble(s[0]));
      pXForm.setZXCoeff01(Double.parseDouble(s[1]));
      pXForm.setZXCoeff10(Double.parseDouble(s[2]));
      pXForm.setZXCoeff11(Double.parseDouble(s[3]));
      pXForm.setZXCoeff20(Double.parseDouble(s[4]));
      pXForm.setZXCoeff21(Double.parseDouble(s[5]));
    }
    if ((hs = atts.get(ATTR_ZX_POST)) != null) {
      String s[] = hs.split(" ");
      pXForm.setZXPostCoeff00(Double.parseDouble(s[0]));
      pXForm.setZXPostCoeff01(Double.parseDouble(s[1]));
      pXForm.setZXPostCoeff10(Double.parseDouble(s[2]));
      pXForm.setZXPostCoeff11(Double.parseDouble(s[3]));
      pXForm.setZXPostCoeff20(Double.parseDouble(s[4]));
      pXForm.setZXPostCoeff21(Double.parseDouble(s[5]));
    }
    if ((hs = atts.get(ATTR_CHAOS)) != null) {
      String s[] = hs.split(" ");
      for (int i = 0; i < s.length; i++) {
        pXForm.getModifiedWeights()[i] = Double.parseDouble(s[i]);
      }
    }
    readMotionCurves(pXForm, atts, null);
    // variations
    {
      List<String> variationNameList = VariationFuncList.getNameList();
      Map<String, String> aliasMap = VariationFuncList.getAliasMap();

      for (XMLAttribute attr : atts.getAttributes()) {
        String rawName = attr.getName();
        String name = removeIndexFromAttr(rawName);
        String varName = name;
        boolean hasVariation = variationNameList.indexOf(varName) >= 0;
        if (!hasVariation) {
          String aliasName = aliasMap.get(name);
          if (aliasName != null) {
            varName = aliasName;
            hasVariation = variationNameList.indexOf(varName) >= 0;
          }
        }
        if (hasVariation) {
          VariationFunc varFunc = VariationFuncList.getVariationFuncInstance(varName);
          Variation variation = pXForm.addVariation(Double.parseDouble(atts.get(rawName)), varFunc);
          String priority = atts.get(varName + "_" + ATTR_FX_PRIORITY);
          if (priority != null && priority.length() > 0) {
            variation.setPriority(Integer.parseInt(priority));
          }
          // ressources 
          {
            final RessourceName[] ressNames = variation.getFunc().getRessourceNames();
            if (ressNames != null) {
              for (RessourceName rName : ressNames) {
                String pHs;
                if ((pHs = atts.get(name + "_" + rName.toString())) != null) {
                  variation.getFunc().setRessource(rName, Tools.hexStringToByteArray(pHs));
                }
              }
            }
          }
          // params
          {
            String paramNames[] = variation.getFunc().getParameterNames();
            String paramAltNames[] = variation.getFunc().getParameterAlternativeNames();
            if (paramNames != null) {
              if (paramAltNames != null && paramAltNames.length != paramNames.length) {
                paramAltNames = null;
              }
              for (int i = 0; i < paramNames.length; i++) {
                String pName = paramNames[i];
                String pHs;
                if ((pHs = atts.get(rawName + "_" + pName)) != null) {
                  variation.getFunc().setParameter(pName, Double.parseDouble(pHs));
                }
                // altNames can only be come from flames which were not created by JWF, so no need to handle index here 
                else if (paramAltNames != null && ((pHs = atts.get(paramAltNames[i])) != null)) {
                  variation.getFunc().setParameter(pName, Double.parseDouble(pHs));
                }
                // curve
                {
                  String namePrefix = rawName + "_" + pName + "_";
                  if (atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_POINT_COUNT) != null) {
                    MotionCurve curve = variation.getMotionCurve(pName);
                    if (curve == null) {
                      curve = variation.createMotionCurve(pName);
                    }
                    readMotionCurveAttributes(atts, curve, namePrefix);
                  }
                }
              }
            }
          }
          // if it's possible that populating of variation function parameters has expanded list of parameters, 
          //    (in other words, that calls to setParameter() has changed what getParameterNames() returns)
          //    then redo parameter population 
          // assumes only one level deep of parameter expansion
          //    that is, if changes to parameter A can cause a parameter B to be dynamically added/removed
          //    then changes to parameter B cannot in turn cause additional parameters to be added/removed
          if (variation.getFunc().dynamicParameterExpansion()) {
            String paramNames[] = variation.getFunc().getParameterNames();
            String paramAltNames[] = variation.getFunc().getParameterAlternativeNames();
            if (paramNames != null) {
              if (paramAltNames != null && paramAltNames.length != paramNames.length) {
                paramAltNames = null;
              }
              for (int i = 0; i < paramNames.length; i++) {
                String pName = paramNames[i];
                String pHs;
                if ((pHs = atts.get(rawName + "_" + pName)) != null) {
                  variation.getFunc().setParameter(pName, Double.parseDouble(pHs));
                }
                // altNames can only be come from flames which were not created by JWF, so no need to handle index here 
                else if (paramAltNames != null && ((pHs = atts.get(paramAltNames[i])) != null)) {
                  variation.getFunc().setParameter(pName, Double.parseDouble(pHs));
                }
                // curve
                {
                  String namePrefix = rawName + "_" + pName + "_";
                  if (atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_POINT_COUNT) != null) {
                    MotionCurve curve = variation.getMotionCurve(pName);
                    if (curve == null) {
                      curve = variation.createMotionCurve(pName);
                    }
                    readMotionCurveAttributes(atts, curve, namePrefix);
                  }
                }
              }
            }
          }
          // curves
          readMotionCurves(variation, atts, rawName + "_");
          //
        }
      }
    }
  }

  protected String removeIndexFromAttr(String pName) {
    int s = pName.indexOf("#");
    if (s < 0) {
      return pName;
    }
    int e = pName.indexOf("#", s + 1);
    if (e < 0) {
      return pName;
    }
    if (e == pName.length() - 1) {
      return pName.substring(0, s);
    }
    else {
      return pName.substring(0, s) + pName.substring(e + 1, pName.length());
    }
  }

  protected void readColors(String flameXML, Layer layer) {
    // Colors
    {
      int p = 0;
      while (true) {
        int ps = flameXML.indexOf("<color ", p + 1);
        if (ps < 0)
          break;
        int pe = flameXML.indexOf("/>", ps + 1);
        String hs = flameXML.substring(ps + 7, pe);
        {
          int index = 0;
          int r = 0, g = 0, b = 0;
          XMLAttributes atts = Tools.parseAttributes(hs);
          String attr;
          if ((attr = atts.get(ATTR_INDEX)) != null) {
            index = Integer.parseInt(attr);
          }
          if ((attr = atts.get(ATTR_RGB)) != null) {
            String s[] = attr.split(" ");
            r = Tools.FTOI(Double.parseDouble(s[0]));
            g = Tools.FTOI(Double.parseDouble(s[1]));
            b = Tools.FTOI(Double.parseDouble(s[2]));
          }
          layer.getPalette().setColor(index, r, g, b);
        }
        p = pe + 2;
      }
    }
    // Palette
    {
      int ps = flameXML.indexOf("<palette ");
      if (ps >= 0) {
        ps = flameXML.indexOf(">", ps + 1);
        int pe = flameXML.indexOf("</palette>", ps + 1);
        String hs = flameXML.substring(ps + 1, pe);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hs.length(); i++) {
          char c = hs.charAt(i);
          if ((c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
            sb.append(c);
          }
          else if (c >= 'a' && c <= 'z') {
            sb.append(Character.toUpperCase(c));
          }
        }
        hs = sb.toString();
        if ((hs.length() % 6) != 0)
          throw new RuntimeException("Invalid/unknown palette");
        int index = 0;
        for (int i = 0; i < hs.length(); i += 6) {
          int r = Integer.parseInt(hs.substring(i, i + 2), 16);
          int g = Integer.parseInt(hs.substring(i + 2, i + 4), 16);
          int b = Integer.parseInt(hs.substring(i + 4, i + 6), 16);
          // System.out.println(hs.substring(i, i + 2) + "#" + hs.substring(i + 2, i + 4) + "#" + hs.substring(i + 4, i + 6));
          // System.out.println("  flame->palette->setColor(" + index + "," + r + "," + g + "," + b + ");");
          layer.getPalette().setColor(index++, r, g, b);
        }
      }
    }
  }

  protected void readFinalXForms(String flameXML, Flame flame, Layer layer) {
    // FinalXForm
    {
      int p = 0;
      while (true) {
        int ps = flameXML.indexOf("<finalxform ", p + 1);
        if (ps < 0)
          break;
        int pe = flameXML.indexOf("</finalxform>", ps + 1);
        if (pe < 0) {
          pe = flameXML.indexOf("/>", ps + 1);
        }
        String hs = flameXML.substring(ps + 7, pe);
        XForm xForm = new XForm();
        parseXFormAttributes(flame, xForm, hs);
        layer.getFinalXForms().add(xForm);
        p = pe + 2;
      }
    }
  }

  protected void readXForms(String flameXML, Flame flame, Layer layer) {
    // XForms
    {
      int p = 0;
      while (true) {
        int ps = flameXML.indexOf("<xform ", p + 1);
        if (ps < 0)
          break;
        int pe = flameXML.indexOf("</xform>", ps + 1);
        if (pe < 0) {
          pe = flameXML.indexOf("/>", ps + 1);
        }
        String hs = flameXML.substring(ps + 7, pe);
        XForm xForm = new XForm();
        parseXFormAttributes(flame, xForm, hs);
        layer.getXForms().add(xForm);
        p = pe + 2;
      }
    }
  }

  protected void readMotionCurves(Object source, XMLAttributes atts, String pNamePrefix) {
    for (MotionCurveAttribute attribute : AnimationService.getAllMotionCurves(source)) {
      MotionCurve curve = attribute.getMotionCurve();
      String namePrefix = pNamePrefix == null ? attribute.getName() + "_" : pNamePrefix + attribute.getName() + "_";
      readMotionCurveAttributes(atts, curve, namePrefix);
    }
  }

  public static void readMotionCurveAttributes(XMLAttributes atts, MotionCurve curve, String namePrefix) {
    String hs;
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_ENABLED)) != null) {
      curve.setEnabled(Boolean.parseBoolean(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_XMIN)) != null) {
      curve.setViewXMin(Integer.parseInt(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_XMAX)) != null) {
      curve.setViewXMax(Integer.parseInt(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_YMIN)) != null) {
      curve.setViewYMin(Double.parseDouble(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_VIEW_YMAX)) != null) {
      curve.setViewYMax(Double.parseDouble(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_INTERPOLATION)) != null) {
      curve.setInterpolation(Interpolation.valueOf(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_SELECTED_IDX)) != null) {
      curve.setSelectedIdx(Integer.parseInt(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_LOCKED)) != null) {
      curve.setLocked(Boolean.parseBoolean(hs));
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_POINT_COUNT)) != null) {
      int pointCount = Integer.parseInt(hs);
      int x[] = new int[pointCount];
      double y[] = new double[pointCount];
      for (int i = 0; i < pointCount; i++) {
        if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_X + i)) != null) {
          x[i] = Integer.parseInt(hs);
        }
        if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_Y + i)) != null) {
          y[i] = Double.parseDouble(hs);
        }
      }
      curve.setPoints(x, y);
    }
    if ((hs = atts.get(namePrefix + AbstractFlameReader.CURVE_ATTR_PARENT_CURVE)) != null) {
      String parentNamePrefix = hs + "_";
      MotionCurve parent = new MotionCurve();
      curve.setParent(parent);
      readMotionCurveAttributes(atts, parent, parentNamePrefix);
    }
  }
}
