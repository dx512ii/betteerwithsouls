package dxii.betterwithsouls.client;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

public class ParticleDamageText extends Particle {
	public ParticleDamageText(World world, double x, double y, double z, double xa, double ya, double za) {
		super(world, x, y, z, xa, ya, za);
	}

	public int[] numbers;

	public ParticleDamageText(int number, World world, double x, double y, double z, double xa, double ya, double za, float scale) {
		super(world, x, y, z, 0.0, 0.0, 0.0);

		this.tex = TextureRegistry.getTexture("betterwithsouls:particle/dmg_nums");

		char[] chars = String.valueOf(number).toCharArray();
		numbers = new int[chars.length];
		int counter = 0;
		for(char c : chars){
			int c_int = c - '0';//char => int
			c_int--;
			if(c_int < 0){
				c_int = 9;
			}
			this.numbers[counter] = c_int;
			counter++;
		}

		this.xd *= 0.1;
		this.yd *= 0.1;
		this.zd *= 0.1;
		this.xd += xa;
		this.yd += ya;
		this.zd += za;
		this.rCol = this.gCol = this.bCol = .5f;
		this.size *= 0.55F;
		this.size *= scale;
		this.lifetime = 100;
		this.lifetime *= (int)scale;
		this.noPhysics = false;
	}

	public void render(Tessellator t, float partialTick, double xOff, double yOff, double zOff, float xa, float ya, float za, float xa2, float za2) {
		int counter = 0;
		for(int num : this.numbers) {
			float umax = (float) this.tex.getIconUMax();
			float vmin = (float) this.tex.getIconVMin();
			float vmax = (float) this.tex.getIconVMax();

			float u0 = umax * 0.1f*(num);
			float u2 = umax * 0.1f*(num+1);
			float v0 = vmin;
			float v2 = vmax;

			float r = 0.1F * this.size;
			double offset = 1 + 1.5*counter;
			double offset2 = 1 + 0.003*counter;

			float x = (float) (this.xo + (this.x - this.xo) * partialTick - xOff);
			float y = (float) (this.yo + (this.y - this.yo) * partialTick - yOff);
			float z = (float) (this.zo + (this.z - this.zo) * partialTick - zOff);
			t.setColorOpaque_F(this.rCol, this.gCol, this.bCol);



			t.addVertexWithUV((x - (xa+xa*offset) * r - xa2 * r), (y - ya * r), (z - (za+za*offset) * r - za2 * r), u2, v2);
			t.addVertexWithUV((x - (xa+xa*offset) * r + xa2 * r), (y + ya * r), (z - (za+za*offset) * r + za2 * r), u2, v0);
			t.addVertexWithUV((x + (xa-xa*offset) * r + xa2 * r), (y + ya * r), (z + (za-za*offset) * r + za2 * r), u0, v0);
			t.addVertexWithUV((x + (xa-xa*offset) * r - xa2 * r), (y - ya * r), (z + (za-za*offset) * r - za2 * r), u0, v2);
			counter++;
		}
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		}


		this.yd += 0.001;
		this.move(this.xd, this.yd, this.zd);
		if (this.y == this.yo) {
			this.xd *= 1.1;
			this.zd *= 1.1;
		}

		this.xd *= 0.96;
		this.yd *= 0.96;
		this.zd *= 0.96;
		if (this.onGround) {
			this.xd *= 0.7;
			this.zd *= 0.7;
		}

	}
}
