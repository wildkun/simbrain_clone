/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2003 Jeff Yoshimi <www.jeffyoshimi.net>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.simnet.neurons;

import org.simnet.interfaces.Neuron;
import org.simnet.interfaces.Synapse;
import org.simnet.neurons.rules.Tanh;
import org.simnet.util.SMath;

/**
 * Additive model of a neuron.  See haykin (2002), section 14.5.  Used with continnuous Hopfield networks.
 */
public class AdditiveNeuron extends Neuron{

	private double lambda = 1.4;
	private double time_step = .1;
	private double resistance = 1;
	
	/**
	 * Default constructor needed for external calls which create neurons then 
	 * set their parameters
	 */
	public AdditiveNeuron() {
	
		this.setUpperBound(1);
		this.setLowerBound(-1);
		this.setIncrement(.1);
	}
	
	/**
	 *  This constructor is used when creating a neuron of one type from another neuron of another type
	 *  Only values common to different types of neuron are copied
	 */
	public AdditiveNeuron(Neuron n) {
		super(n);
	}
		
	public Neuron duplicate() {
		AdditiveNeuron bn = new AdditiveNeuron();
		return super.duplicate(bn);
	}
	
	/**
	 * Update buffer of additive neuron using Euler's method
	 */
	public void update() {
		double act = 0;
		if (getFanIn().size() > 0) {
			for (int j = 0; j < getFanIn().size(); j++) {
				Synapse w = (Synapse) getFanIn().get(j);
				Neuron source = w.getSource();
				act += w.getStrength() * SMath.arctan(source.getActivation(), lambda);
			}
		}
		double val = -getActivation()/resistance + act + getInputValue();  	
		setBuffer(getActivation() +  time_step * val);

	}
	

	public static String getName() {return "Additive";}

	/**
	 * @return Returns the lambda.
	 */
	public double getLambda() {
		return lambda;
	}
	/**
	 * @param lambda The lambda to set.
	 */
	public void setLambda(double lambda) {
		this.lambda = lambda;
	}
	/**
	 * @return Returns the time_step.
	 */
	public double getTime_step() {
		return time_step;
	}
	/**
	 * @param time_step The time_step to set.
	 */
	public void setTime_step(double time_step) {
		this.time_step = time_step;
	}
	/**
	 * @return Returns the resistance.
	 */
	public double getResistance() {
		return resistance;
	}
	/**
	 * @param resistance The resistance to set.
	 */
	public void setResistance(double resistance) {
		this.resistance = resistance;
	}
}