/*--------------------------------------------------------------------------------------
 Ethanon Engine (C) Copyright 2008-2013 Andre Santee
 http://ethanonengine.com/

	Permission is hereby granted, free of charge, to any person obtaining a copy of this
	software and associated documentation files (the "Software"), to deal in the
	Software without restriction, including without limitation the rights to use, copy,
	modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
	and to permit persons to whom the Software is furnished to do so, subject to the
	following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
	INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
	PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
	HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
	CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
	OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
--------------------------------------------------------------------------------------*/

#ifndef ETH_REVOLUTE_JOINT_H_
#define ETH_REVOLUTE_JOINT_H_

#include "ETHJoint.h"

#include "ETHPhysicsController.h"

class ETHRevoluteJoint : public ETHJoint
{
public:
	ETHRevoluteJoint(const gs2d::str_type::string& jointName, const enml::File& file, ETHPhysicsSimulator& simulator, ETHEntity* entityA, ETHEntity* entityB);
	~ETHRevoluteJoint();
	void KillJoint();

	static b2Vec2 ComputeAnchorPosition(ETHEntity *entity, const Vector2& anchorPoint);
	b2Joint* GetB2Joint();
	void Destroy();
private:
	b2RevoluteJoint* m_joint;
	b2World* m_world;
};

typedef boost::shared_ptr<ETHRevoluteJoint> ETHRevoluteJointPtr;

#endif
