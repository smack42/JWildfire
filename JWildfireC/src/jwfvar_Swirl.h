/*
 JWildfireC - an external C-based fractal-flame-renderer for JWildfire
 Copyright (C) 2012 Andreas Maschke

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

#ifndef JWFVAR_SWIRL_H_
#define JWFVAR_SWIRL_H_

#include "jwf_Variation.h"

class SwirlFunc: public Variation {
public:
	SwirlFunc() {
	}

	const char* getName() const {
		return "swirl";
	}

	void transform(FlameTransformationContext *pContext, XForm *pXForm, XYZPoint *pAffineTP, XYZPoint *pVarTP, float pAmount) {
    float r2 = pAffineTP->x * pAffineTP->x + pAffineTP->y * pAffineTP->y;
    float c1 = sinf(r2);
    float c2 = cosf(r2);
    pVarTP->x += pAmount * (c1 * pAffineTP->x - c2 * pAffineTP->y);
    pVarTP->y += pAmount * (c2 * pAffineTP->x + c1 * pAffineTP->y);
    if (pContext->isPreserveZCoordinate) {
      pVarTP->z += pAmount * pAffineTP->z;
    }
	}

	SwirlFunc* makeCopy() {
		return new SwirlFunc(*this);
	}

};

#endif // JWFVAR_SWIRL_H_
