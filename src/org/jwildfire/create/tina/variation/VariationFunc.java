/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2011 Andreas Maschke

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

import java.io.Serializable;
import java.util.EnumMap;

import org.jwildfire.create.tina.base.Layer;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

@SuppressWarnings("serial")
public abstract class VariationFunc implements Serializable {

  public VariationFunc() {
      initRessources();
  }

  public void init(FlameTransformationContext pContext, Layer pLayer, XForm pXForm, double pAmount) {
      // nothing here - may be overridden
  }

  public int getPriority() {
    return 0;
  }

  public abstract void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount);

  public abstract String getName();

  public abstract String[] getParameterNames();

  // For few variations like mobius which use a different naming scheme 
  public String[] getParameterAlternativeNames() {
    return null;
  }

  public abstract Object[] getParameterValues();

  public abstract void setParameter(String pName, double pValue);

  
  /** just a helper class to store both type and value in the map **/
  protected static class RessourceTypeValuePair {
      public final RessourceType type;
      public byte[] value;
      public RessourceTypeValuePair(RessourceType type, byte[] value) {
          this.type = type;
          this.value = value;
      }
  }
  
  /** must be filled with all available RessourceName values in initRessources() **/
  protected final EnumMap<RessourceName, RessourceTypeValuePair> ressources = new EnumMap<>(RessourceName.class);
  
  /**
   * used in initRessources() to initialize all available ressources
   * @param name  
   * @param type
   * @param value
   */
  protected void addRessource(RessourceName name, RessourceType type, byte[] value) {
      this.ressources.put(name, new RessourceTypeValuePair(type, value));
  }
  
  /**
   * return the value of the specified ressource
   * @param name
   * @return the value, or null if the specified RessourceName is not known
   */
  protected byte[] getResourceValue(RessourceName name) {
      final RessourceTypeValuePair r = this.ressources.get(name);
      return r == null ? null : r.value;
  }
  
  /**
   * return the value of the specified ressource as a String
   * @param name
   * @return the value as a String, or "" (empty String) if the specified RessourceName is not known
   */
  protected String getResourceValueString(RessourceName name) {
      final byte[] r = getResourceValue(name);
      return r == null ? "" : new String(r);
  }
  
  /**
   * set the value of the specified ressource.
   * throws IllegalArgumentException if the specified RessourceName is not known
   * @param name
   * @param value
   */
  protected void setResourceValue(RessourceName name, byte[] value) {
      final RessourceTypeValuePair r = this.ressources.get(name);
      if (r != null) {
          r.value = value;
      } else {
          throw new IllegalArgumentException(name.toString());
      }
  }

  /**
   * initialize the property "ressources".
   * must be overridden by implementing classes.
   */
  protected void initRessources() {
      // nothing here - must be overridden by implementing classes
  }
  /**
   * set the specified ressource to the specified value.
   * does nothing if this VariationFunc doesn't know the specified RessourceName.
   * must be overridden by implementing classes.
   * @param rName
   * @param rValue
   */
  public void setRessource(RessourceName rName, byte[] rValue) {
      // nothing here - must be overridden by implementing classes
  }

  /**
   * return all RessourceName values
   * @return an on-demand created array of RessourceName, or null of no RessourceName are available
   */
  public RessourceName[] getRessourceNames() {
    if (this.ressources.isEmpty()) {
      return null;
    } else {
      return this.ressources.keySet().toArray(new RessourceName[this.ressources.size()]);
    }
  }

  /**
   * return the specified ressource
   * @param rName
   * @return the ressource, or null if the specified RessourceName is not known
   */
  public byte[] getRessource(RessourceName rName) {
    final RessourceTypeValuePair r = this.ressources.get(rName);
    return r == null ? null : r.value;
  }

  /**
   * return the type of the specified ressource
   * @param rName
   * @return  the RessourceType (
   */
  public RessourceType getRessourceType(RessourceName rName) {
    final RessourceTypeValuePair r = this.ressources.get(rName);
    return r == null ? null : r.type;
  }


  public Object getParameter(String pName) {
    int idx = getParameterIndex(pName);
    return idx >= 0 ? getParameterValues()[idx] : null;
  }

  public int getParameterIndex(String pName) {
    String paramNames[] = getParameterNames();
    if (paramNames != null) {
      for (int i = 0; i < paramNames.length; i++) {
        if (paramNames[i].equals(pName)) {
          return i;
        }
      }
    }
    return -1;
  }

  public static double limitVal(double pValue, double pMin, double pMax) {
    if (pValue < pMin) {
      return pMin;
    }
    else if (pValue > pMax) {
      return pMax;
    }
    else {
      return pValue;
    }
  }

  public static int limitIntVal(int pValue, int pMin, int pMax) {
    if (pValue < pMin) {
      return pMin;
    }
    else if (pValue > pMax) {
      return pMax;
    }
    else {
      return pValue;
    }
  }

  public Object[] joinArrays(Object[] array1, Object[] array2) {
    Object[] joinedArray = new Object[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  public String[] joinArrays(String[] array1, String[] array2) {
    String[] joinedArray = new String[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  public void validate() {

  }

  public VariationFunc makeCopy() {
    VariationFunc varCopy = VariationFuncList.getVariationFuncInstance(this.getName());
    // params
    String[] paramNames = this.getParameterNames();
    if (paramNames != null) {
      for (int i = 0; i < paramNames.length; i++) {
        Object val = this.getParameterValues()[i];
        if (val instanceof Number) {
          varCopy.setParameter(paramNames[i], ((Number)val).doubleValue());
        }
        else {
          throw new IllegalStateException();
        }
      }
    }
    // ressources
    final RessourceName[] ressNames = this.getRessourceNames();
    if (ressNames != null) {
      for (final RessourceName rName : ressNames) {
        varCopy.setRessource(rName, this.getRessource(rName));
      }
    }
    return varCopy;
  }
  
  /** 
   * if resourceCanModifyParams is true, it means that variation function
   *   can dynamically add (or remove) parameters, depending on values of resource resourceName
   *  in other words, calls to setRessource(resourceName, ...)) 
   *       can change what is returned by getParameterNames() and getParameterValues()
   */
  public boolean ressourceCanModifyParams(String resourceName) { return false; }
  
  /** should return true if at least one resource can trigger parameter addition/removal */
  public boolean ressourceCanModifyParams()  { return false; }  
  
  /**
   * if dynamicParameterExpansion is true, it means that variation function 
   *   can dynamically add (or remove) parameters, depending on values of parameter paramName
   *       in other words, calls to setParameter(paramName, ...)) 
   *       can change what is returned by getParameterNames() and getParameterValues()
   * current implementation to handle this assumes only one level depth of parameter expansion 
   *       that is, if change to parameter paramName can cause a parameter B to be dynamically added/removed
   *       then changes to parameter B cannot in turn cause additional parameters to be added/removed
   */
  public boolean dynamicParameterExpansion(String paramName) { return false; }
  
  /** should return true if at least one parameter can trigger parameter addition/removal */
  public boolean dynamicParameterExpansion() { return false; }
  
}
