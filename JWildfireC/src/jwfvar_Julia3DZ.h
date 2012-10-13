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


#include "jwf_Constants.h"
#include "jwf_Variation.h"

class Julia3DZFunc: public Variation {
public:
	Julia3DZFunc() {
		power = 3.0;
		initParameterNames(1, "power");
	}

	const char* getName() const {
		return "julia3Dz";
	}

	void setParameter(char *pName, JWF_FLOAT pValue) {
		if (strcmp(pName, "power") == 0) {
			power = pValue;
		}
	}

	void init(FlameTransformationContext *pContext, XForm *pXForm, JWF_FLOAT pAmount) {
		_absPower = abs(FTOI(power));
		_cPower = 1.0 / power * 0.5;
	}

	void transform(FlameTransformationContext *pContext, XForm *pXForm, XYZPoint *pAffineTP, XYZPoint *pVarTP, JWF_FLOAT pAmount) {
		JWF_FLOAT r2d = pAffineTP->x * pAffineTP->x + pAffineTP->y * pAffineTP->y;
		JWF_FLOAT r = pAmount * JWF_POW(r2d, _cPower);

		int rnd = (int) (pContext->randGen->random() * _absPower);
		JWF_FLOAT angle = (JWF_ATAN2(pAffineTP->y, pAffineTP->x) + 2.0 * M_PI * rnd) / power;
		JWF_FLOAT sina, cosa;
		JWF_SINCOS(angle, &sina, &cosa);
		pVarTP->x += r * cosa;
		pVarTP->y += r * sina;
		pVarTP->z += r * pAffineTP->z / (sqrtf(r2d) * _absPower);
	}

	Julia3DZFunc* makeCopy() {
		return new Julia3DZFunc(*this);
	}

private:
	JWF_FLOAT power;
	JWF_FLOAT _absPower, _cPower;
};

